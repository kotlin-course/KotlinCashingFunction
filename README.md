# Kotlin Cashing Function
I wanted to write a simple function which should have been able to be used somewhat like this:

```kotlin
fun fib(n:Long) = cashing {
	if (n <= 1) return n
	return fib(n-1) + f(n-2)
}
```

But alas, no such luck, and after digging around for a while it turns out to be a concrete example of a much larger issue. At the core of it is that there is no way to call a lambda expression recursively, no way to get a reference to it, and hence no way to pass manipulate it.

There are ways around the issue though, but at the cost of readability and elegance. 

In the functional programming world it can be solved using a ["Y-combinator"](https://rosettacode.org/wiki/Y_combinator). Compared to other programming languages Kotlin solves this in relative elegance, but I find it a cludge.

The general issue is also related to the deeper semantics of `this`. Wikipediad has a page on [Anonymous recursion](https://en.wikipedia.org/wiki/Anonymous_recursion) that also show this to be a strangely hard problem.

Adding an argument to the `caching` function:

```kotlin
fun <T,R> cached(value: T, body: () -> R) : R {
  // check if value is in cache
  val result = body()
  // save result in cache
  return result
  }

fun fib(n: Long): Long = cached(n) {
  if (n < 2) n
  else fib(n - 1) + fib(n - 2)
  }
```



