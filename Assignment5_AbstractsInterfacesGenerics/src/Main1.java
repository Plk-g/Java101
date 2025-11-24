import person.Developer;
import person.Tester;
import person.TechWriter;
import task.Task;

import java.util.ArrayList;
import java.util.Random;

//The `Main1` class initializes an `ArrayList<Task>` called `jobQueue` and creates instances of `Developer`, `Tester`, and 
//`TechWriter`, passing the shared job queue to each. It then generates five random tasks for each person, assigning a 
//random 4-digit module name and a random time (1-10 minutes) before adding the tasks to the queue. 
//The program then iterates over `jobQueue`, calling `performTask()` for each task and summing up the total time required to 
//complete all tasks. Finally, it prints the total processing time for all tasks in the queue.

public class Main1 {
    public static void main(String[] args) {
        ArrayList<Task> jobQueue = new ArrayList<>();

        Developer dev = new Developer(jobQueue);
        Tester tester = new Tester(jobQueue);
        TechWriter writer = new TechWriter(jobQueue);

        Random random = new Random();

        for (int i = 0; i < 5; i++) {
            String moduleName = String.format("%04d", random.nextInt(10000));
            int time = random.nextInt(10) + 1;
            dev.startTask(moduleName, time);

            moduleName = String.format("%04d", random.nextInt(10000));
            time = random.nextInt(10) + 1;
            tester.startTask(moduleName, time);

            moduleName = String.format("%04d", random.nextInt(10000));
            time = random.nextInt(10) + 1;
            writer.startTask(moduleName, time);
        }

        int totalTime = 0;
        for (Task task : jobQueue) {
            task.performTask();
            totalTime += task.getTime();
        }

        System.out.println("Total time to process all tasks: " + totalTime + " minutes");
    }
}
