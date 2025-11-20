package PartII;

import java.util.concurrent.ThreadLocalRandom;

public class Gradebook {
    private final Student[] roster;

    public Gradebook(int capacity) {
        if (capacity <= 0) throw new IllegalArgumentException("capacity must be > 0");
        this.roster = new Student[capacity];
    }

    /** add a student to the first available null slot */
    public void addStudent(Student s) {
        if (s == null) return;
        for (int i = 0; i < roster.length; i++) {
            if (roster[i] == null) {
                roster[i] = s;
                return;
            }
        }
        // silently ignore if full (or you could print a message)
    }

    /** return the student with this id or null if not found */
    public Student findById(int id) {
        for (Student s : roster) {
            if (s != null && s.getId() == id) return s;
        }
        return null;
    }

    /** student with the highest average; null if none */
    public Student getTopStudent() {
        Student best = null;
        double bestAvg = Double.NEGATIVE_INFINITY;
        for (Student s : roster) {
            if (s == null) continue;
            double avg = s.getAverage();
            if (avg > bestAvg) {
                bestAvg = avg;
                best = s;
            }
        }
        return best;
    }

    /** print all students (id, name, grades, avg) */
    public void printAll() {
        for (Student s : roster) {
            if (s != null) System.out.println(s);
        }
    }

    // demo per assignment instructions
    public static void main(String[] args) {
        Gradebook gb = new Gradebook(5);

        // create & add 5 students
        gb.addStudent(new Student("Ada Lovelace"));
        gb.addStudent(new Student("Alan Turing"));
        gb.addStudent(new Student("Grace Hopper"));
        gb.addStudent(new Student("Katherine Johnson"));
        gb.addStudent(new Student("Donald Knuth"));

        // set random grades 0..100 for 5 assignments
        ThreadLocalRandom rng = ThreadLocalRandom.current();
        for (Student s : gb.roster) {
            if (s == null) continue;
            for (int i = 0; i < 5; i++) {
                s.setGrades(i, rng.nextInt(0, 101));
            }
        }

        // print all
        System.out.println("=== Gradebook ===");
        gb.printAll();

        // top student
        Student top = gb.getTopStudent();
        System.out.println("\nTop student:");
        System.out.println(top == null ? "(none)" : top);
    }
}
