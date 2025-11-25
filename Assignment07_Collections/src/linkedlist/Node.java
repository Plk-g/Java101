package linkedlist;

final class Node<T> {
    T val;
    Node<T> next;

    Node(T val) { this.val = val; }

    @Override
    public String toString() {
        return String.valueOf(val);
    }
}
