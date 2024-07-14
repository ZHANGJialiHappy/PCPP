# Exercise 1.1

## Mandatory

1. Output: range from 10000000 to 20000000. No, I don't get expected result 20000000.
2. Because it doesn't iterate so many times, so the possibility of race condition is much lower.
3. No, because they are essentially the same with the following code.

```
int temp = count;  (1)
count = temp + 1;  (2)
```

4. The codes showed in the question 3 are critical section for the thread. Because of lock, the race condition caused by interleavings will not happen. That means the critical sections exluded mutually, and excuted sequentially by the same thread.
5. Only the defined critical section influence shared data causing race condition.

## Challenging

6. cd src/main/java/exercises01' -> javac LongCounter.java -> javap -c LongCounter.class

- the output verifies my explanation

7.

- The result should be between -10 million and 10 million.
- The reason is the same with question 4. count-- is also the critical section.

8. ```
   int temp = count;  (1)
   count = temp + 1;  (2)
   ```

- In the same thread, it must be (1) -> (2)
- Between the threads or above block of codes, after finishing above the above codes, it can be unlocked. That means t(2) always happens before t(1)
- Conclusion: because of lock, ...->\[t1(1)->t1(2)\]_->\[t2(1)->t2(2)\]_->...

9. assum we set count = a; the minimum value is between (10 million + a) and (20 million + a). Without lock, it can be \[t1(1), t2(1), t1(2), t2(2)\]\*

# Exercise 1.2

## Mandatory

1. see main/java/exercises01/PrinterThreads
2. ```
      System.out.print("-"); (1)
      try {
          Thread.sleep(50);
      } catch (InterruptedException exn) {
      }
      System.out.print("|"); (2)
   ```
   Without lock, the above code for t1 and t2 will interleave. For example, it can be t1(1), t2(1), t1(2), t2(2).
3. When the above codes are locked, the locked codes in thread must finished, then can be unlocked. It avoids interleaving problem.

## Challenging

4. Look at above code in 1.2.2

- In the same thread, it must be (1) -> (2)
- Between the threads or above block of codes, after finishing above the above codes, it can be unlocked. That means, out of the block, t(2) always happens before t(1)
- Conclusion: because of lock, ...->\[t1(1)->t1(2)\]_->\[t2(1)->t2(2)\]_->...

# Exercise 1.2

## Mandatory

## Challenging
