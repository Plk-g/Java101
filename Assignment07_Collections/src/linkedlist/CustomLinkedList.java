package linkedlist;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Random;


public class CustomLinkedList<T> implements Queue<T> {

    //Node used internally for the singly linked chain.
    private static final class Node<E> {
        E v;
        Node<E> next;
        Node(E v) { this.v = v; }
    }

    private Node<T> head;   
    private Node<T> tail;  
    private int size;

    public CustomLinkedList() {}

    // Queue core

    @Override
    public boolean offer(T val) {             
        Node<T> n = new Node<>(val);
        if (tail == null) {
            head = tail = n;
        } else {
            tail.next = n;
            tail = n;
        }
        size++;
        return true;
    }

    @Override
    public boolean add(T val) {               
        return offer(val);
    }

    @Override
    public T poll() {                         
        if (head == null) return null;
        T v = head.v;
        head = head.next;
        if (head == null) tail = null;
        size--;
        return v;
    }

    @Override
    public T remove() {
        T v = poll();
        if (v == null) throw new NoSuchElementException("queue is empty");
        return v;
    }

    @Override
    public T peek() {                         
        return head == null ? null : head.v;
    }

    @Override
    public T element() {                      
        T v = peek();
        if (v == null) throw new NoSuchElementException("queue is empty");
        return v;
    }

    /** Returns the element at position index (0-based). */
    public T get(int index) throws IndexOutOfBoundsException {
        checkIndex(index);
        Node<T> cur = head;
        for (int i = 0; i < index; i++) cur = cur.next;
        return cur.v;
    }

    /** Removes and returns the element at position index (0-based). */
    public T remove(int index) throws IndexOutOfBoundsException {
        checkIndex(index);
        Node<T> prev = null, cur = head;
        for (int i = 0; i < index; i++) { prev = cur; cur = cur.next; }
        if (prev == null) {
            // removing head
            head = cur.next;
            if (head == null) tail = null;
        } else {
            prev.next = cur.next;
            if (cur == tail) tail = prev;
        }
        size--;
        return cur.v;
    }

    private void checkIndex(int idx) {
        if (idx < 0 || idx >= size) throw new IndexOutOfBoundsException("index " + idx + " out of " + size);
    }

    //Collection plumbing (lightweight)

    @Override
    public int size() { return size; }

    @Override
    public boolean isEmpty() { return size == 0; }

    @Override
    public void clear() { head = tail = null; size = 0; }

    @Override
    public boolean contains(Object v) {
        for (T x : this) if (x == null ? v == null : x.equals(v)) return true;
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            Node<T> cur = head;
            @Override public boolean hasNext() { return cur != null; }
            @Override public T next() {
                if (cur == null) throw new NoSuchElementException();
                T v = cur.v; cur = cur.next; return v;
            }
            @Override public void remove() { throw new UnsupportedOperationException(); }
        };
    }

    @Override
    public Object[] toArray() {
        Object[] arr = new Object[size];
        int i = 0;
        for (T v : this) arr[i++] = v;
        return arr;
    }

    @Override
    public <E> E[] toArray(E[] a) {
        if (a.length < size) {
            @SuppressWarnings("unchecked")
            E[] na = (E[]) java.lang.reflect.Array
                    .newInstance(a.getClass().getComponentType(), size);
            a = na;
        }
        int i = 0;
        for (T v : this) {
            @SuppressWarnings("unchecked") E e = (E) v;
            a[i++] = e;
        }
        if (a.length > size) a[size] = null;
        return a;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        boolean changed = false;
        for (T x : c) changed |= add(x);
        return changed;
    }

    @Override
    public boolean remove(Object obj) {       // remove first match, if any
        Node<T> prev = null, cur = head;
        while (cur != null) {
            if (obj == null ? cur.v == null : obj.equals(cur.v)) {
                if (prev == null) head = cur.next; else prev.next = cur.next;
                if (cur == tail) tail = prev;
                size--;
                return true;
            }
            prev = cur; cur = cur.next;
        }
        return false;
    }

    @Override public boolean containsAll(Collection<?> c) { for (Object o : c) if (!contains(o)) return false; return true; }
    @Override public boolean removeAll(Collection<?> c) { throw new UnsupportedOperationException(); }
    @Override public boolean retainAll(Collection<?> c) { throw new UnsupportedOperationException(); }

    //Pretty printing 

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<T> cur = head;
        while (cur != null) {
            if (cur != head) sb.append(", ");
            sb.append(cur.v);
            cur = cur.next;
        }
        return sb.append(']').toString();
    }

    //Tiny benchmark

    public static void main(String[] args) {
        final int COUNT = 1_000_000;
        Random r = new Random(7);

        // JDK LinkedList
        long t0 = System.nanoTime();
        java.util.LinkedList<Integer> jdk = new java.util.LinkedList<>();
        for (int i = 0; i < COUNT; i++) jdk.add(r.nextInt());
        long t1 = System.nanoTime();

        // Our CustomLinkedList
        r.setSeed(7); // same data for fairness
        CustomLinkedList<Integer> mine = new CustomLinkedList<>();
        for (int i = 0; i < COUNT; i++) mine.add(r.nextInt());
        long t2 = System.nanoTime();

        System.out.printf("time for library LL : %.3f ms%n", (t1 - t0) / 1_000_000.0);
        System.out.printf("time for custom  LL : %.3f ms%n", (t2 - t1) / 1_000_000.0);

        // quick sanity check: peek, get, remove(index)
        System.out.println("peek() = " + mine.peek());
        System.out.println("get(10) = " + mine.get(10));
        mine.remove(10);
        System.out.println("after remove(10), new get(10) = " + mine.get(10));
        System.out.println("size = " + mine.size());
    }
}
