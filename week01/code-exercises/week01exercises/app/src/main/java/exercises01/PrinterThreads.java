package exercises01;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PrinterThreads {
    Printer p = new Printer();

    public PrinterThreads() {
        Thread t1 = new Thread(() -> {
            while (true) {
                p.print();
            }
        });
        Thread t2 = new Thread(() -> {
            while (true) {
                p.print();
            }
        });
        t1.start();
        t2.start();
        // infinite, so no need to join.
        // try {
        // t1.join();
        // t2.join();
        // } catch (InterruptedException e) {
        // System.out.println("Error " + e.getMessage());
        // e.printStackTrace();
        // }
    }

    public static void main(String[] args) {
        new PrinterThreads();
    }

    class Printer {
        Lock lock = new ReentrantLock();

        public void print() {
            lock.lock();
            try {
                System.out.print("-");
                try {
                    Thread.sleep(50);
                } catch (InterruptedException exn) {
                }
                System.out.print("|");
            } finally {
                lock.unlock();
            }

        }
    }
}
