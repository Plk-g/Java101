import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;


public class Aggregator implements Runnable {
	private final Map<String, Boolean> seenIds = new HashMap<>();
    private final Map<AggKey, Long> totals = new HashMap<>();
    private final BlockingQueue<Msg> inQueue;
    private final int numValidators;

    public Aggregator(BlockingQueue<Msg> inQueue, int numValidators) {
        this.inQueue = inQueue;
        this.numValidators = numValidators;
    }

	@Override
	public void run() {
        if (inQueue == null) {
            return;
        }
        int stopCount = 0;
        try {
            while (true) {
                Msg msg = inQueue.take();
                if (msg instanceof Stop) {
                    stopCount++;
                    if (stopCount >= numValidators) {
                        break;
                    }
                    continue;
                }
                if (!(msg instanceof MsgTx)) {
                    continue;
                }
                MsgTx m = (MsgTx) msg;
                Tx t = m.tx;

                if (t.id != null && !t.id.isEmpty()) {
                    if (seenIds.containsKey(t.id)) {
                        continue;
                    }
                    seenIds.put(t.id, Boolean.TRUE);
                }

                LocalDate day = Instant.ofEpochSecond(t.epochSec)
                        .atZone(ZoneOffset.UTC)
                        .toLocalDate();
                AggKey key = new AggKey(t.account, day);
                totals.merge(key, t.cents, Long::sum);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
	}
	
	Map<AggKey, Long> snapshotTotals() {
		return new HashMap<>(totals);
	}

}