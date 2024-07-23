// For week 2
// sestoft@itu.dk * 2014-08-25
// raup@itu.dk * 2021-09-03
package exercises02;

public class TestMutableInteger2 {

	public static void main(String[] args) {
		final MutableInteger2 mi = new MutableInteger2();
		Thread t = new Thread(() -> {
			while (mi.get() == 0) {
				System.out.println("do nothing");
			} // Loop while zero
			System.out.println("I completed, mi = " + mi.get());
		});
		t.start();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		mi.set(42);
		System.out.println("mi set to 42, waiting for thread ...");
		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Thread t completed, and so does main");
	}
}

class MutableInteger2 {
	// WARNING: Not ready for usage by concurrent programs
	volatile private int value = 0;

	public void set(int value) {
		this.value = value;
	}

	public int get() {
		return value;
	}
}
