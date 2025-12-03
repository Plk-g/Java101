import java.nio.file.*;
import java.time.*;
import java.util.*;
import java.util.concurrent.*;

/**
 * Graduate-level Java pipeline:
 *   Reader (CSV) -> Validator(workers) -> Aggregator(single)
 * Backpressure via bounded queues, graceful shutdown, error routing, de-dup for exactly-once aggregation.
 *
 * Usage:
 *   java PipelineApp <csvPath> [q1Cap=256] [q2Cap=256] [validators=2] [errorOut=errors.csv]
 */

public class PipelineApp {

    private static final int QUEUE_CAPACITY = 256;
    private static final int NUM_VALIDATORS = 2;

    // ====== Main driver ======
    public static void main(String[] args) throws Exception {

        if (args.length < 1) {
            System.err.println("Usage: java PipelineApp <csvPath>");
            return;
        }

        Path csvPath = Paths.get(args[0]);

        // Queues for stages
        BlockingQueue<Msg> readerToValidators = new LinkedBlockingQueue<>(QUEUE_CAPACITY);
        BlockingQueue<Msg> validatorsToAggregator = new LinkedBlockingQueue<>(QUEUE_CAPACITY);
        BlockingQueue<Msg> validatorsToErrorWriter = new LinkedBlockingQueue<>(QUEUE_CAPACITY);

        //the stages
        Reader reader = new Reader(csvPath, readerToValidators, NUM_VALIDATORS);

        Validator[] validators = new Validator[NUM_VALIDATORS];
        for (int i = 0; i < NUM_VALIDATORS; i++) {
            validators[i] = new Validator(
                    readerToValidators,
                    validatorsToAggregator,
                    validatorsToErrorWriter
            );
        }

        ErrorWriter errorWriter = new ErrorWriter(
                validatorsToErrorWriter,
                Paths.get("errors.csv"),
                NUM_VALIDATORS
        );
        Aggregator agg = new Aggregator(
                validatorsToAggregator,
                NUM_VALIDATORS
        );

        // Threads for each stage
        Thread readerThread = new Thread(reader, "reader");
        Thread[] validatorThreads = new Thread[NUM_VALIDATORS];
        for (int i = 0; i < NUM_VALIDATORS; i++) {
            validatorThreads[i] = new Thread(validators[i], "validator-" + i);
        }
        Thread errorThread = new Thread(errorWriter, "error-writer");
        Thread aggThread = new Thread(agg, "aggregator");

        // Initiate the pipeline
        aggThread.start();
        errorThread.start();
        for (Thread t : validatorThreads) {
            t.start();
        }
        readerThread.start();

        readerThread.join();
        for (Thread t : validatorThreads) {
            t.join();
        }
        aggThread.join();
        errorThread.join();

        // once the threads are done, you should
        // be able to get all of the data from the
        // aggregator
        Map<AggKey, Long> totals = agg.snapshotTotals();

        // Print top 10 aggregates
        System.out.println("Top 10 aggregates:");
        totals.entrySet().stream()
                .sorted(Comparator
                        .comparingLong((Map.Entry<AggKey, Long> e) -> e.getValue()).reversed()
                        .thenComparing(e -> e.getKey().account)
                        .thenComparing(e -> e.getKey().day))
                .limit(10)
                .forEach(e -> System.out.printf(
                        "%s          -> %d cents%n",
                        e.getKey(),
                        e.getValue()));

        // Print errors
        List<String> errors = errorWriter.snapshotErrors();
        System.out.println();
        System.out.println("Errors (" + errors.size() + "):");
        for (String line : errors) {
            System.out.println(line);
        }
    }
}

//====== Data model ======
final class Tx {
    final String id;
    final String account;
    final long epochSec;
    final long cents;
    Tx(String id, String account, long epochSec, long cents) {
        this.id = id; this.account = account; this.epochSec = epochSec; this.cents = cents;
    }
    public String toString() { return id + "," + account + "," + epochSec + "," + cents; }
}

final class AggKey {
    final String account;
    final LocalDate day; 
    AggKey(String account, LocalDate day) { this.account = account; this.day = day; }
    @Override public boolean equals(Object o) {
        if (!(o instanceof AggKey)) return false;
        AggKey k = (AggKey)o;
        return Objects.equals(account, k.account) && Objects.equals(day, k.day);
    }
    @Override public int hashCode() { return Objects.hash(account, day); }
    @Override public String toString() { return account + " @ " + day; }
}

// ====== Messaging between stages ======
interface Msg {}

final class MsgTx implements Msg {
    final Tx tx;
    final long t0Nanos;
    MsgTx(Tx tx, long t0Nanos) { this.tx = tx; this.t0Nanos = t0Nanos; }
}

// New message type for invalid records going to ErrorWriter
final class MsgError implements Msg {
    final Tx tx;
    final String reason;
    MsgError(Tx tx, String reason) {
        this.tx = tx;
        this.reason = reason;
    }
}

final class Stop implements Msg {
    static final Stop INSTANCE = new Stop();
    private Stop() {}
}
