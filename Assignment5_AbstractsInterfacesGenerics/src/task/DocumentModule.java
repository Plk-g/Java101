package task;

public class DocumentModule extends Task {
    public DocumentModule(String module, int time) {
        super(module, time);
    }

    @Override
    public void performTask() {
        System.out.println("Documenting Module " + module);
    }
}
