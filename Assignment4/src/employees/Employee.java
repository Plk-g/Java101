package employees;

import java.util.Objects;

public class Employee {
    private static int count = 0;   
    private final int id;
    private String name;
    private String department;
    private double salary;

    public Employee(String name, String department, double salary) {
        this.id = ++count;
        this.name = name;
        this.department = department;
        this.salary = salary;
    }

 
    public int getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }

   
    public void work() {
        System.out.println(name + " is doing general work.");
    }

    public void displayInfo() {
        System.out.println(toString());
    }

    @Override
    public String toString() {
        return "Employee#" + id + " [name=" + name + ", dept=" + department + ", salary=" + salary + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;
        Employee e = (Employee) o;
        return id == e.id &&
               Objects.equals(name, e.name) &&
               Objects.equals(department, e.department) &&
               salary == e.salary;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, department, salary);
    }
}
