# Running (for example)

1. cd root
2. $ gradle -PmainClass=exercises02.TestLocking0 run
3. $ gradle -PmainClass=exercises02.TestMutableInteger run

# Exercise 2.1

## Mandatory

1. look at code in FairReadWriteMonitor.Java
2. to check if writersWaiting==0 ensures readers can't cut inline. For example, w1 w2 r1 are coming in the line. After unlock w1, if there is no writersWaiting r1 can more possibility to cut in line and acquire lock before w2 (look at the original code following).

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
2. With Java Intrinsic Locks (synchronized), it establishs a happen-before relation enforces visibility

- In the program below, it holds

```
while(mi.get==0) -> mi.set(42)
or
mi.set(42) -> while(mi.get==0)
```

- the CPU is notallowed to keep the value of mi in the register of the CPU or cache and must flush it to main memory.

3. No, if main thread and t thread run in different CPU and the data is not flushed into main memory immediately:

- lack of happens-before relation between operations
- In the program below, it holds:

```
t (while(mi.get==0)) ↛ main(mi.set(42))
or
main(mi.set(42)) ↛ t (while(mi.get==0))
```

- Consequently, the CPU is allowed to keep the value of running in the register of the CPU or cache and not flush it to main memory

4. Volatile variables in terms of reads/writes and happens-before

- A writeto a volatile variable happens before any subsequentreadto the volatile variable:

```
mi.set(42) -> while(mi.get==0)
```

since there is

```
try {
		Thread.sleep(100);
	} catch (InterruptedException e) {
		e.printStackTrace();
	}
```

so while(mi.get==0)b {}, run 100 ms, than mi.set(42) -> while(mi.get==0)

5. already use happens before answered.
