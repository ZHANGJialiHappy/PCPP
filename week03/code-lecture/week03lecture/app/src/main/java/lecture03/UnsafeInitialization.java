// Week 3
// raup@itu.dk * 19/09/2021
package lecture03;

import java.util.Objects;

class TestUnsafeInitialization {
	public TestUnsafeInitialization() {
		int N = 10_000;
		for (int i = 0; i < N; i++) {

			UnsafeInitialization u = new UnsafeInitialization();

			new Thread(() -> {
				if (u.readX() != 42)
					System.out.println("x is not equal 42");
			}).start();

			new Thread(() -> {
				if (Objects.isNull(u.readO()))
					System.out.println("o is null");
			}).start();
		}
	}

	public static void main(String[] args) {
		new TestUnsafeInitialization();
	}

}

class UnsafeInitialization {
	// private volatile int x;
	// volatile 关键字保证了变量的可见性和有序性。当一个变量被声明为 volatile 时，它有两个主要效果：
	// 可见性和禁止重排序，AtomicInteger
	// private volatile Object o;
	// 一旦对象的 final 字段被分配了值，并且构造函数完成了，那么其他线程总是可以看到正确的值。
	// 可见性和不可变性，AtomicReferenceclass
	private int x;
	private Object o;

	public UnsafeInitialization() {
		this.x = 42;
		this.o = new Object();
	}

	public int readX() {
		return this.x;
	}

	public Object readO() {
		return this.o;
	}
}