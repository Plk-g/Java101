package PartII;

import java.util.Arrays;

public class Student {
    private static int NEXT_ID = 1;         private final int id;
    private final String name;

    // exactly 5 slots; -1 means "no grade yet"
    private final int[] grades = new int[5];

    public Student(String name) {
        this.id = NEXT_ID++;
        this.name = name == null ? "Unnamed" : name.trim();
        Arrays.fill(grades, -1);
    }

    public int getId() {                 
        return id;
    }

    public String getName() {
        return name;
    }

    /** returns a COPY, not the internal array */
    public int[] getGrades() {
        return Arrays.copyOf(grades, grades.length);
    }

    
    public void setGrades(int assignmentIndex, int grade) {
        if (assignmentIndex < 0 || assignmentIndex >= grades.length) return;
        if (grade < 0 || grade > 100) return;
        grades[assignmentIndex] = grade;
    }

    /** mean over existing (non -1) grades; returns 0.0 if none exist */
    public double getAverage() {
        int sum = 0, count = 0;
        for (int g : grades) {
            if (g >= 0) { sum += g; count++; }
        }
        return count == 0 ? 0.0 : (sum * 1.0) / count;
    }

    @Override
    public String toString() {
        // show grades compactly; -1 appears as “–”
        StringBuilder sb = new StringBuilder();
        sb.append("#").append(id).append(" ").append(name).append(" | ");
        for (int i = 0; i < grades.length; i++) {
            sb.append("A").append(i + 1).append(":")
              .append(grades[i] == -1 ? "–" : grades[i]);
            if (i < grades.length - 1) sb.append("  ");
        }
        sb.append("  | avg=").append(String.format("%.2f", getAverage()));
        return sb.toString();
    }
}
