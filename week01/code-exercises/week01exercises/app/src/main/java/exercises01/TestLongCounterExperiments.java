// For week 1
// sestoft@itu.dk * 2014-08-21
// raup@itu.dk * 2021-08-27
package exercises01;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestLongCounterExperiments {

	LongCounter lc = new LongCounter();
	int counts = 10000000;

	public TestLongCounterExperiments() {

		Thread t1 = new Thread(() -> {
			for (int i = 0; i < counts; i++) {
				lc.increment();

				// lock.lock();
				// try {
				// lc.increment();
				// } finally {
				// lock.unlock();
				// }
			}
			// for (int i = 0; i < 10000000; i++) {
			// lc.decrement();
			// }
		});
		Thread t2 = new Thread(() -> {
			for (int i = 0; i < counts; i++) {
				lc.increment();

				// lock.lock();
				// try {
				// lc.increment();
				// } finally {
				// lock.unlock();
				// }
			}
			// for (int i = 0; i < 10000000; i++) {
			// lc.decrement();
			// }
		});
		t1.start();
		t2.start();
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException exn) {
			System.out.println("Some thread was interrupted");
		}
		System.out.println("Count is " + lc.get());
	}

	public static void main(String[] args) {
		new TestLongCounterExperiments();
	}

	class LongCounter {
		private long count = 10000000;
		Lock lock = new ReentrantLock();

		public void increment() {
			// lock.lock();
			// try {
			count = count + 1;
			// } finally {
			// lock.unlock();
			// }
		}

		// public void decrement() {
		// lock.lock();
		// try {
		// count--;
		// } finally {
		// lock.unlock();
		// }
		// }

		public long get() {
			return count;
		}
	}
}
