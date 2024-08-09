package lecture03;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ThreadSafePrincipal {
    public static void main(String[] args) {
        UnsafeList l = new UnsafeList();
        l.getList().add(5);
        System.out.println(l.getList());
    }

}

// To analyse thread-safe in a class, we must identify/consider:

// • Identify the class state
// Explanation:
// In this Counter class, the state is represented by the count field.
class Counter2 {
    private int count = 0;

    public void increment() {
        count++;
    }

    public int getCount() {
        return count;
    }
}

// • Make sure that mutable class state does not escape
// Explanation:
// Here, if the getList method exposes the internal list directly, allowing
// external code to modify it without synchronization, leading to potential race
// conditions.
class UnsafeList {
    private List<Integer> list = Collections.synchronizedList(new ArrayList<>());

    public List<Integer> getList() {
        return list; // Mutable state escapes
    }
}

// • Ensure safe publication
class UnsafePublication {
    private Holder holder;

    public void initialize() {
        holder = new Holder(42); // Holder may not be safely published
        new Thread(() -> {
            if (holder.getValue() == 0) {
                System.out.println("can't view value");
            }
        }).start();
    }

    public Holder getHolder() {
        return holder;
    }
}

class Holder {
    private int value;

    public Holder(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

// • Whenever possible define class state as immutable
// The class is declared final, which means no other class can extend
// ImmutablePoint. This ensures that the design and guarantees of immutability
// are not altered by subclassing.
final class ImmutablePoint {
    private final int x;
    private final int y;

    public ImmutablePoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

// • If class state must be mutable, ensure mutual exclusion
class SynchronizedCounter {
    private int count = 0;

    public synchronized void increment() {
        count++;
    }

    public synchronized int getCount() {
        return count;
    }
}
