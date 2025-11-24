package task;

public abstract class Task {
    protected String module;
    protected int time;

    public Task(String module, int time) {
        this.module = module;
        this.time = time;
    }

    public int getTime() {
        return time;
    }

    public abstract void performTask();
}
