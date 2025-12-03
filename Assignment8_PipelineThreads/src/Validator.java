import java.util.Optional;
import java.util.concurrent.BlockingQueue;

public class Validator implements Runnable {

    private final BlockingQueue<Msg> inQueue;
    private final BlockingQueue<Msg> okQueue;
    private final BlockingQueue<Msg> errorQueue;

    public Validator(BlockingQueue<Msg> inQueue,
                     BlockingQueue<Msg> okQueue,
                     BlockingQueue<Msg> errorQueue) {
        this.inQueue = inQueue;
        this.okQueue = okQueue;
        this.errorQueue = errorQueue;
    }

    // No-arg constructor kept, but not used in the final pipeline
    public Validator() {
        this.inQueue = null;
        this.okQueue = null;
        this.errorQueue = null;
    }

    @Override
    public void run() {
        if (inQueue == null) {
            return;
        }
        try {
            while (true) {
                Msg msg = inQueue.take();
                if (msg instanceof Stop) {
                    // Pass the stop signal along so the next stages know to shut down.
                    if (okQueue != null) okQueue.put(Stop.INSTANCE);
                    if (errorQueue != null) errorQueue.put(Stop.INSTANCE);
                    break;
                }
                if (!(msg instanceof MsgTx)) {
                    continue;
                }
                MsgTx m = (MsgTx) msg;
                Tx t = m.tx;

                Optional<String> maybeErr = validate(t);
                if (maybeErr.isPresent()) {
                    if (errorQueue != null) {
                        errorQueue.put(new MsgError(t, maybeErr.get()));
                    }
                } else {
                    if (okQueue != null) {
                        okQueue.put(m);
                    }
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
	
    static Optional<String> validate(Tx t) {
        if (t.id == null || t.id.isEmpty()) return Optional.of("missing_id");
        if (t.account == null || t.account.isEmpty()) return Optional.of("missing_account");
        if (t.epochSec < 946684800L || t.epochSec >= 4102444800L) return Optional.of("bad_time"); // 2000..2100
        if (Math.abs(t.cents) > 10_000_00L) return Optional.of("amount_out_of_range");
        return Optional.empty();
    }

}
