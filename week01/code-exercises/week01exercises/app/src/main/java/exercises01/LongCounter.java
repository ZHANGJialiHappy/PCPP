package exercises01;

public class LongCounter {
    private long count = 0;

    public void increment() {
        count = count + 1;
    }

    public void incrementPlusPlus() {
        count++;
    }

    public void incrementPlusEquals() {
        count += 1;
    }

    public long get() {
        return count;
    }

    public static void main(String[] args) {
        LongCounter lc = new LongCounter();
        lc.increment();
        lc.incrementPlusEquals();
        lc.incrementPlusPlus();
        System.out.printf("The count is %d", lc.get());
    }
}
