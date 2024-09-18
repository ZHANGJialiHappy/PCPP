package exercises04;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.BrokenBarrierException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;

import static org.junit.jupiter.api.Assertions.*;

class SemaphoreImpTest {

    private static final int THREADCOUNT = 16;
    private static final int SEMAPHORE_CAPACITY = 1;
    private SemaphoreImp semaphore;
    private CyclicBarrier barrier;
    private CountDownLatch endLatch;

    @BeforeEach
    public void setUp() {
        semaphore = new SemaphoreImp(SEMAPHORE_CAPACITY);
        barrier = new CyclicBarrier(THREADCOUNT + 1); // +1 for the main thread
        endLatch = new CountDownLatch(THREADCOUNT);
    }

    @RepeatedTest(100)
    public void testRaceConditionInAcquireRelease() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(THREADCOUNT);
        for (int i = 0; i < THREADCOUNT; i++) {
            new Thread(() -> {
                try {
                    barrier.await(); // synchronize start
                    semaphore.acquire();
                    assertTrue(semaphore.getState() <= SEMAPHORE_CAPACITY,
                            "Semaphore state exceeded capacity during acquire.");
                    Thread.sleep(50);
                    semaphore.release();
                    assertTrue(semaphore.getState() >= 0 && semaphore.getState() <= SEMAPHORE_CAPACITY,
                            "Semaphore state should be between 0 and capacity.");
                    barrier.await(); // synchronize end
                    endLatch.countDown();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                } finally {
                    latch.countDown(); // Decrement latch count
                }
            }).start();
        }

        try {
            barrier.await(); // Let both threads start together
            barrier.await(); // Wait for both threads to finish
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }

        latch.await();
    }
}
