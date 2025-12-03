import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class ErrorWriter implements Runnable {

    private final BlockingQueue<Msg> inQueue;
    private final Path outputFile;
    private final int numValidators;
    private final List<String> errors = new ArrayList<>();

    public ErrorWriter(BlockingQueue<Msg> inQueue, Path outputFile, int numValidators) {
        this.inQueue = inQueue;
        this.outputFile = outputFile;
        this.numValidators = numValidators;
    }

    // No-arg constructor kept, but not used by the final pipeline
    public ErrorWriter() {
        this.inQueue = null;
        this.outputFile = null;
        this.numValidators = 0;
    }

	@Override
	public void run() {
        if (inQueue == null || outputFile == null) {
            return;
        }
        int stopCount = 0;
        try (BufferedWriter bw = Files.newBufferedWriter(outputFile)) {
            // header row
            bw.write("id,account,epochSec,cents,reason");
            bw.newLine();

            while (true) {
                Msg msg = inQueue.take();
                if (msg instanceof Stop) {
                    stopCount++;
                    if (stopCount >= numValidators) {
                        break;
                    }
                    continue;
                }
                if (!(msg instanceof MsgError)) {
                    continue;
                }
                MsgError err = (MsgError) msg;
                Tx t = err.tx;
                String line = t.id + "," + t.account + "," + t.epochSec + "," + t.cents + "," + err.reason;
                bw.write(line);
                bw.newLine();
                errors.add(line);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

    public List<String> snapshotErrors() {
        return new ArrayList<>(errors);
    }

}
