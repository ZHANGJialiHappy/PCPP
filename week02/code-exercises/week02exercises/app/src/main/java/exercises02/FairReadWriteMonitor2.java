package exercises02;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class FairReadWriteMonitor2 {
    private final ReadWriteLock rwLock = new ReentrantReadWriteLock(true);

    public void readLock() {
        rwLock.readLock().lock();
    }

    public void readUnlock() {
        rwLock.readLock().unlock();
    }

    public void writeLock() {
        rwLock.writeLock().lock();
    }

    public void writeUnlock() {
        rwLock.writeLock().unlock();
    }
}
