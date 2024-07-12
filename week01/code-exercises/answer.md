# Exercise 1.1

## Mandatory

1. Output: range from 10000000 to 20000000. No, I don't get expected result 20000000.
2. Because it doesn't iterate so many times, so the possibility in race condition is much lower.
3. No, because they are essentially the same with the following code.

```
int temp = count;
count = temp + 1;
```
