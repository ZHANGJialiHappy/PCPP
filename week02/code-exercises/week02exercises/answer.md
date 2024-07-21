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

3. yes, I've implemented above.

# Exercise 2.2
