package lecture03;

import java.util.concurrent.Semaphore;

public class SemaphoreAsLock {
    private final Semaphore semaphore = new Semaphore(1);

    public void criticalSection() {
        try {
            // Acquire the semaphore (like locking)
            semaphore.acquire();
            // Critical section
            System.out.println(Thread.currentThread().getName() + " is in the critical section");
            Thread.sleep(1000); // Simulate some work
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            // Release the semaphore (like unlocking)
            semaphore.release();
        }
    }

    public static void main(String[] args) {
        SemaphoreAsLock lock = new SemaphoreAsLock();

        Runnable task = () -> {
            for (int i = 0; i < 5; i++) {
                lock.criticalSection();
            }
        };

        Thread t1 = new Thread(task, "Thread-1");
        Thread t2 = new Thread(task, "Thread-2");

        t1.start();
        t2.start();
    }
}
