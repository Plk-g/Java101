package task;

public class TestModule extends Task {
    public TestModule(String module, int time) {
        super(module, time);
    }

    @Override
    public void performTask() {
        System.out.println("Testing Module " + module);
    }
}
