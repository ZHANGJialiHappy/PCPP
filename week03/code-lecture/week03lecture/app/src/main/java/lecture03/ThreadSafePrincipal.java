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
// • Whenever possible define class state as immutable
// • If class statemust be mutable, ensure mutual exclusion
