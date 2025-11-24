package person;

import task.TestModule;
import task.Task;
import java.util.ArrayList;

public class Tester extends Person {
    public Tester(ArrayList<Task> jobQueue) {
        super(jobQueue);
    }

    @Override
    public void startTask(String moduleName, int time) {
        jobQueue.add(new TestModule(moduleName, time));
    }
}
