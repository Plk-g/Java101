package task;

public class DevelopModule extends Task {
    public DevelopModule(String module, int time) {
        super(module, time);
    }

    @Override
    public void performTask() {
        System.out.println("Developing Module " + module);
    }
}
