package employees;

public class Intern extends Employee {
    private String school;
    private int graduationYear;

    public Intern(String name, String department, double stipend, String school, int graduationYear) {
        super(name, department, stipend); // stipend treated as salary
        this.school = school;
        this.graduationYear = graduationYear;
    }

    public String getSchool() { return school; }
    public void setSchool(String school) { this.school = school; }

    public int getGraduationYear() { return graduationYear; }
    public void setGraduationYear(int graduationYear) { this.graduationYear = graduationYear; }

    @Override
    public void work() {
        System.out.println(getName() + " (Intern) is learning and helping out.");
    }

    @Override
    public String toString() {
        return "Intern -> " + super.toString() + ", school=" + school + ", gradYear=" + graduationYear;
    }
}
