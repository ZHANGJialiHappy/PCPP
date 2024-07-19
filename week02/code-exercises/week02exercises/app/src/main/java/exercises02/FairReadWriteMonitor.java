// For week 2
// raup@itu.dk * 01/09/2021
package exercises02;

public class FairReadWriteMonitor {
	private int readsAcquires = 0;
	private int readsReleases = 0;
	private boolean writer = false;

	//////////////////////////
	// Read lock operations //
	//////////////////////////

	public synchronized void readLock() {
		try {
			while (writer)
				wait();
			readsAcquires++;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public synchronized void readUnlock() {
		readsReleases++;
		if (readsAcquires == readsReleases)
			notifyAll();
	}

	///////////////////////////
	// Write lock operations //
	///////////////////////////

	public synchronized void writeLock() {
		try {
			while (writer)
				wait();
			writer = true;
			while (readsAcquires != readsReleases)
				wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public synchronized void writeUnlock() {
		writer = false;
		notifyAll();
	}

}
