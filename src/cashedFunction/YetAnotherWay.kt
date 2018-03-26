package cashedFunction

fun main(args: Array<String>) {
  println("Fibonacci  20: "+fib(20))
  println("Factorial   8: "+fact(8))
  println("Factorial  10: "+fact(10))
  }

fun caller() = with(Thread.currentThread().stackTrace[3]) { methodName /* toString() */ }

object cache {
  val map = mutableMapOf<String, Any>()
  operator fun get(caller: String, value: Any): Any? {
    val key = "$caller/$value"
    return map[key]
    }
  operator fun set(caller: String, value: Any, result: Any) {
    val key = "$caller/$value"
    map[key] = result
    }
  }

fun <T,R> cached(value: T, body: () -> R) : R {
  val caller = caller()
  val saved = cache[caller, value as Any]
  if (cache[caller, value as Any] == null) {
    val result = body()
    cache[caller, value as Any] = result as Any
    println("calculated value of $caller($value) is $result")
    return result
    }
  else {
    println("    cached value of $caller($value) is $saved")
    return saved as R
    }
  }

fun fib(n: Long): Long = cached(n) {
  if (n < 2) n
  else fib(n - 1) + fib(n - 2)
  }

fun fact(n: Long): Long = cached(n) {
  if (n == 0L) 1L
  else n*fact(n - 1)
  }