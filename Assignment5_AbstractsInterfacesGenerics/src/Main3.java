import triplet.Triplet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

//The `Main3` class demonstrates the use of generics and lambda expressions by working with a parameterized `Triplet` class. 
//It initializes an `ArrayList` of `Triplet<Double, Double, Double>` objects and populates it with 10 randomly generated triplets, 
//where each value is a random double between 0 and 10. The program first prints the unsorted triplets, then sorts them using a 
//lambda expression that compares their magnitudes, and prints the sorted list. 
//It then sorts the triplets again using `Collections.sort(triplets, null)`, which utilizes the `compareTo` method implemented 
//in the `Triplet` class, and prints the final sorted list.

public class Main3 {
    public static void main(String[] args) {
        ArrayList<Triplet<Double, Double, Double>> triplets = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            double first = random.nextDouble() * 10;
            double second = random.nextDouble() * 10;
            double third = random.nextDouble() * 10;
            triplets.add(new Triplet<>(first, second, third));
        }

        System.out.println("\nUnsorted Triplets:");
        for (Triplet<Double, Double, Double> t : triplets) {
            System.out.println(t);
        }

        triplets.sort((t1, t2) -> Double.compare(t1.getMagnitude(), t2.getMagnitude()));

        System.out.println("\nSorted Triplets by Magnitude (Lambda Comparator):");
        for (Triplet<Double, Double, Double> t : triplets) {
            System.out.println(t);
        }

        Collections.sort(triplets, null);

        System.out.println("\nSorted Triplets by Magnitude (Using Comparable):");
        for (Triplet<Double, Double, Double> t : triplets) {
            System.out.println(t);
        }
    }
}
