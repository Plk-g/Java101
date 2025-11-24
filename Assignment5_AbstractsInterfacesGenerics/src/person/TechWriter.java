package person;

import task.DocumentModule;
import task.Task;
import java.util.ArrayList;

public class TechWriter extends Person {
    public TechWriter(ArrayList<Task> jobQueue) {
        super(jobQueue);
    }

    @Override
    public void startTask(String moduleName, int time) {
        jobQueue.add(new DocumentModule(moduleName, time));
    }
}
