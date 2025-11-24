package triplet;

public class Triplet<A extends Comparable<A>, B extends Comparable<B>, C extends Comparable<C>> implements Comparable<Triplet<A, B, C>> {
    private A first;
    private B second;
    private C third;

    public Triplet(A first, B second, C third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public A getFirst() {
        return first;
    }

    public B getSecond() {
        return second;
    }

    public C getThird() {
        return third;
    }

    public void setFirst(A first) {
        this.first = first;
    }

    public void setSecond(B second) {
        this.second = second;
    }

    public void setThird(C third) {
        this.third = third;
    }

    // Calculate magnitude for numeric values
    public double getMagnitude() {
        if (first instanceof Number && second instanceof Number && third instanceof Number) {
            double x = ((Number) first).doubleValue();
            double y = ((Number) second).doubleValue();
            double z = ((Number) third).doubleValue();
            return Math.sqrt(x * x + y * y + z * z);
        }
        return 0;
    }

    @Override
    public int compareTo(Triplet<A, B, C> other) {
        return Double.compare(this.getMagnitude(), other.getMagnitude());
    }

    @Override
    public String toString() {
        return "(" + first + ", " + second + ", " + third + " | Magnitude: " + getMagnitude() + ")";
    }
}
