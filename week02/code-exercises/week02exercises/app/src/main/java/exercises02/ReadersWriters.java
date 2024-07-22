package exercises02;

public class ReadersWriters {

    public ReadersWriters() {
        FairReadWriteMonitor2 m = new FairReadWriteMonitor2();

        final int numReadersWriters = 10;

        for (int i = 0; i < numReadersWriters; i++) {
            // start a writer
            new Thread(() -> {
                m.writeLock();
                System.out.println(" Writer " + Thread.currentThread().getId() + " started writing");
                // write
                System.out.println(" Writer " + Thread.currentThread().getId() + " stopped writing");
                m.writeUnlock();
            }).start();
            // start a writer
            new Thread(() -> {
                m.writeLock();
                System.out.println(" Writer " + Thread.currentThread().getId() + " started writing");
                // write
                System.out.println(" Writer " + Thread.currentThread().getId() + " stopped writing");
                m.writeUnlock();
            }).start();
            // start a reader
            new Thread(() -> {
                m.readLock();
                System.out.println(" Reader " + Thread.currentThread().getId() + " started reading");
                // read
                System.out.println(" Reader " + Thread.currentThread().getId() + " stopped reading");
                m.readUnlock();
            }).start();

        }
    }

    public static void main(String[] args) {
        new ReadersWriters();
    }

}
