# Running (for example)

1. cd root
2. $ gradle -PmainClass=exercises02.TestLocking0 run
3. $ gradle -PmainClass=exercises02.TestMutableInteger run

# Exercise 2.1

## Mandatory

1. look at code in FairReadWriteMonitor.Java
2. to check if writersWaiting==0 ensures readers can't cut inline. For example, w1 w2 r1 are coming in the line. After unlock w1, if there is no writersWaiting, r1 can more possibility to cut in line and acquire lock before w2 (look at the original code following).

```
    public void readLock() {
		lock.lock();
		try {
			while(writer)
				condition.await();
			readsAcquires++;
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		finally {
			lock.unlock();
		}
    }
```

```
public void writeLock() {
		lock.lock();
		try {
			while(writer)
				condition.await();
			writer=true;
			while(readsAcquires != readsReleases)
				condition.await();
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		finally {
			lock.unlock();
		}
    }

```

so we writersWaiting++ before a writer acquire a lock, and check if writersWaiting == 0 to avoid reader cut inline. For example, W1, W2, R3 are inline, after unlocking w1, exactly because writersWaiting++ in W2, writersWaiting== 1, so W2,R3 acquire lock eventually.

## Challenging

3. I use ReentrantReadWriteLock(look at FairReadWriteMonitor2). I fail to use ReentrantLock or intrinsic java locks (synchronized) to avoid starvation. FairReadWriteMonitor can cause starvation of reader. If I use readersWaiting == 0 to avoid writer to cut in line, it will cause deadlock.

# Exercise 2.2

## Mandatory

1. Yes, it loops forever, because main thread and t thread may run in different cpus, so t thread can't view mi's value is setted to 42 in main thread.

- lack of happens-before relation between operations
- In the program below, it holds:

```
t (while(mi.get==0)) ↛ main(mi.set(42))
or
main(mi.set(42)) ↛ t (while(mi.get==0))
```

because

```
t thread ∣∣ main
```

- Consequently, the CPU is allowed to keep the value of running in the register of the CPU or cache and not flush it to main memory

2. With Java Intrinsic Locks (synchronized), it establishs a happen-before relation enforces visibility

- In the program below, it holds

```
while(mi.get==0) -> mi.set(42)
or
mi.set(42) -> while(mi.get==0)
```

- the CPU is not allowed to keep the value of mi in the register of the CPU or cache and must flush it to main memory.

3. No, if main thread and t thread run in different CPU, and Without synchronized on get(), thread t is not guaranteed to terminate because it may not see the updated value of 42 in the value field due to visibility issues. Therefore, making get() synchronized is crucial for ensuring that thread t terminates as expected.

4. Volatile variables in terms of reads/writes and happens-before

- A write to a volatile variable happens before any subsequent read to the volatile variable:

```
mi.set(42) -> while(mi.get==0)
```

since there is

```
try {
		Thread.sleep(500);
	} catch (InterruptedException e) {
		e.printStackTrace();
	}
```

so while(mi.get==0) {}, run 500 ms, than mi.set(42) -> while(mi.get==0)

5. Operations and Expected Happens-Before Relationships:

```
[while(mi.get==0)]\* -> mi.set(42) -> while(mi.get==0)
```

Other happens-before pairs:<br/>

```
t.start() in main -> first action in thread t
```

```
last action in thread t -> t.join() in main
```

Reason that t thread does not terminate, see answer 2.2.1. <br/>
To conclution: if it lacks required happens-before pair, which is mi.set(42) -> while(mi.get==0), it can't terminate.

6. list happens before pairs:

```
t.start() -> first action in thread t
```

```
mi.set(42) -> subsequent actions in main after mi.set(42)
```

```
last action in thread t -> t.join() in main
```

may be

```
mi.set(42) ↛ while(mi.get==0)
```

Lack of the required Happens-Before pair:

```
mi.set(42) -> while(mi.get==0)
```

7. list happens before pairs:

```
t.start() -> first action in thread t
```

```
mi.set(42) -> any subsequent reads of value in thread t
```

```
last action in thread t -> t.join() in main
```

Has the required Happens-Before pair:

```
mi.set(42) -> any subsequent reads of value in thread t
```

# Exercise 2.3

## Mandatory

1. Yes, there is race condition.
2. Instance Methods

- When you synchronize an instance method (public synchronized void addInstance(double x)), it locks on the instance of the object (this).
- Each object instance has int own intrinsic lock. Thus, different instances of the same class do not share the same lock.<br />
  <br />
  Static Methods
- When you synchronize a static method (public static synchronized void addStatic(double x)), it locks on the Class object representing the class (Mystery.class).
- All static synchronized methods of the class share the same lock, regardless of how many instances of the class exist.
  Conclusion: Even though both methods are synchronized, they do not synchronize on the same lock, but both update sum.

3. Static Lock Object:
   <br />
   private static final Object lock = new Object();
   A single static lock object is used for synchronization, ensuring that all accesses to the sum field are thread-safe.
4. Yes, there is race condition.
   <br/>
   Without synchronization, there can be a situation where one thread reads sum while another thread is updating it. This can result in inconsistent or stale values being read.
