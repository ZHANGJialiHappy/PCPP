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

- In the same thread, (1) -> (2)
- Between the threads, e.g. t1 and t2 , t1(2) happens before t2(1)
- Conclusion: because of lock, t1(1)->t1(2)->t2(1)->t2(2). that mean \[𝑡𝑥1,𝑡𝑥2\]
