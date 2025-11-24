import person.Developer;
import person.Tester;
import person.TechWriter;
import task.Task;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Collectors;

//The `Main2` class extends `Main1` by incorporating lambda expressions for sorting and filtering tasks. 
//It initializes an `ArrayList<Task>` (`jobQueue`), creates instances of `Developer`, `Tester`, and `TechWriter`, 
//and generates five random tasks for each, assigning a random 4-digit module name and a random duration (1-10 minutes).
//The job queue is then sorted by task time using a lambda expression with `Comparator`, and a filtered list is created 
//using a lambda-based `Predicate` to retain only tasks with a duration greater than 5. 
//It prints the sorted and filtered tasks, executes all tasks, and computes the total processing time.

public class Main2 {
    public static void main(String[] args) {
        ArrayList<Task> jobQueue = new ArrayList<>();

        Developer dev = new Developer(jobQueue);
        Tester tester = new Tester(jobQueue);
        TechWriter writer = new TechWriter(jobQueue);

        Random random = new Random();

        // Generating 5 random tasks for each person
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

        // Sorting jobQueue by time using a lambda expression
        jobQueue.sort(Comparator.comparingInt(Task::getTime));

        System.out.println("\nSorted Job Queue by Task Time:");
        for (Task task : jobQueue) {
            System.out.println(task.getClass().getSimpleName() + " - " + task.getTime() + " minutes");
        }

        // Using a lambda expression to filter tasks with time > 5
        Predicate<Task> timeFilter = task -> task.getTime() > 5;
        List<Task> filteredTasks = jobQueue.stream().filter(timeFilter).collect(Collectors.toList());

        System.out.println("\nFiltered Job Queue (Tasks with Time > 5):");
        for (Task task : filteredTasks) {
            System.out.println(task.getClass().getSimpleName() + " - " + task.getTime() + " minutes");
        }

        // Executing tasks
        int totalTime = 0;
        for (Task task : jobQueue) {
            task.performTask();
            totalTime += task.getTime();
        }

        System.out.println("\nTotal time to process all tasks: " + totalTime + " minutes");
    }
}
