import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.BlockingQueue;

public class Reader implements Runnable {

    private final Path inputFile;
    private final BlockingQueue<Msg> outQueue;
    private final int numValidators;

    // Constructor used in PipelineApp: new Reader(csvPath, readerToValidators, NUM_VALIDATORS)
    public Reader(Path inputFile, BlockingQueue<Msg> outQueue, int numValidators) {
        this.inputFile = inputFile;
        this.outQueue = outQueue;
        this.numValidators = numValidators;
    }

    public Reader() {
        this.inputFile = null;
        this.outQueue = null;
        this.numValidators = 0;
    }

    @Override
    public void run() {
        if (inputFile == null || outQueue == null) {
            return;
        }

        try (BufferedReader br = Files.newBufferedReader(inputFile)) {
            String line;
            boolean first = true;

            while ((line = br.readLine()) != null) {
                // Skip header row if present
                if (first && line.toLowerCase().contains("id")) {
                    first = false;
                    continue;
                }
                first = false;
                Tx tx = parse(line);
                MsgTx msg = new MsgTx(tx, System.nanoTime());
                outQueue.put(msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            // one Stop per validator 
            if (outQueue != null) {
                for (int i = 0; i < numValidators; i++) {
                    try {
                        outQueue.put(Stop.INSTANCE);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }
        }
    }

    static Tx parse(String line) {
        String[] p = line.split(",", -1);
        if (p.length < 4) {
            // Put a clearly bad row; validator will route it
            return new Tx("", "", 0L, 0L);
        }
        long epochSecs;
        long cents; 
        try {
            epochSecs = Long.parseLong(p[2].trim());
            cents = Long.parseLong(p[3].trim());
        } 
        catch (Exception e) {
            System.err.print("error parsing line " + line);
            return new Tx("", "", 0L, 0L);
        }
        return new Tx(
        		p[0].trim(), 
        		p[1].trim(),
                epochSecs,
                cents
        );
    }

}