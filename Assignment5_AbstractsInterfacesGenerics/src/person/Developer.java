package person;

import task.DevelopModule;
import task.Task;
import java.util.ArrayList;

public class Developer extends Person {
    public Developer(ArrayList<Task> jobQueue) {
        super(jobQueue);
    }

    @Override
    public void startTask(String moduleName, int time) {
        jobQueue.add(new DevelopModule(moduleName, time));
    }
}
