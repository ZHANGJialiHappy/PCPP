package exercises04;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

// JUnit testing imports
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;

import static org.junit.jupiter.api.Assertions.*;

public class ConcurrentSetTest {
    private ConcurrentIntegerSet set;
    private CyclicBarrier barrier;
    private int myInt = 10;
    private static final int THREADCOUNT = 16;

    @BeforeEach
    public void initialize() {
        // set = new ConcurrentIntegerSetBuggy();
        // set = new ConcurrentIntegerSetSync();
        set = new ConcurrentIntegerSetLibrary();
    }

    @RepeatedTest(5000)
    @DisplayName("Rmove Parallel")
    public void TestingAdd() {
        barrier = new CyclicBarrier(THREADCOUNT + 1);

        for (int i = 0; i < THREADCOUNT; i++) {
            new Thread(() -> {
                try {
                    barrier.await();
                    set.add(myInt);
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        try {
            barrier.await();
            barrier.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(1, set.size());
    }

    @RepeatedTest(5000)
    @DisplayName("Rmove Parallel")
    public void testingRemove() {

        barrier = new CyclicBarrier(THREADCOUNT + 1);
        set.add(myInt);
        for (int i = 0; i < THREADCOUNT; i++) {
            new Thread(() -> {
                try {
                    barrier.await();
                    set.remove(myInt);
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        try {
            barrier.await();
            barrier.await();
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertEquals(0, set.size());

    }

}
