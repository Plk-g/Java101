package person;

import java.util.ArrayList;
import task.Task;

public abstract class Person {
    protected ArrayList<Task> jobQueue;

    public Person(ArrayList<Task> jobQueue) {
        this.jobQueue = jobQueue;
    }

    public abstract void startTask(String moduleName, int time);
}
