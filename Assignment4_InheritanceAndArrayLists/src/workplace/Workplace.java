package workplace;

import employees.*;
import java.util.ArrayList;

public class Workplace {
    private String name;
    private ArrayList<Employee> employees;

    public Workplace(String name) {
        this.name = name;
        this.employees = new ArrayList<>();
    }

    public void addEmployee(Employee e) {
        if (e != null) employees.add(e);
    }

    public void listEmployees() {
        System.out.println("=== Employees at " + name + " ===");
        for (Employee e : employees) {
            System.out.println(e);
        }
    }

    public void processWorkday() {
        System.out.println("=== Workday at " + name + " ===");
        for (Employee e : employees) {
            e.work(); // polymorphism
        }
    }

    public static void main(String[] args) {
        Workplace company = new Workplace("Orchid Labs");

        Employee manager = new Employee("Morgan Lee", "Manager", 150000);
        Employee emp1 = new Employee("Riley Chen", "Engineering", 120000);
        Employee emp2 = new Employee("Sam Patel", "Support", 85000);
        Intern intern1 = new Intern("Taylor Kim", "Engineering", 30000, "NYU", 2027);
        Intern intern2 = new Intern("Jordan Fox", "Design", 28000, "Columbia", 2026);

        company.addEmployee(manager);
        company.addEmployee(emp1);
        company.addEmployee(emp2);
        company.addEmployee(intern1);
        company.addEmployee(intern2);

        company.listEmployees();
        company.processWorkday();
    }
}
