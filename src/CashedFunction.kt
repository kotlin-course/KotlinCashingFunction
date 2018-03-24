package cashedFunction

import java.math.BigInteger
import kotlin.math.abs

typealias LongFunc = (Long) -> Long

fun main(args: Array<String>) {

    val N :Long = 92 // Largest fibonacci in a long, Using the Y-combinator style
    val fibN = cache(::fib)(N)
    println("CachFibonacci of $N is $fibN")
    val bigN : Long = 200
    println("Big Fibonacci of $bigN is ${rawFibonacci(bigN)}")

    // Using the Y-combinator style
    print("Factorial(1..10)   : ")
    for (i in 1L..10L) print("${cache(::fac)(i)}  ")
    print("\nFibonacci(1..10)   : ")
    for (i in 1L..10L) print("${cache(::fib)(i)}  ")
    println()
}
// Plain iterative Fibonacci function
fun rawFibonacci(N:Long):BigInteger{
    if ( N <= 1L) return N.toBigInteger();
    var terms = Pair(BigInteger.ZERO,BigInteger.ONE)
    var count = N
    while(count>1L){
        terms = Pair(terms.second , terms.first + terms.second)
        count--
    }
    return terms.second
}

// This is the head-ache inducing Y-combinator adapted from
// https://rosettacode.org/wiki/Y_combinator#Kotlin
fun cache(f: (LongFunc) -> LongFunc): LongFunc {
    val cache = LongCache()
    class RecursiveFunc(val function: (RecursiveFunc) -> LongFunc)
    val recursiveFunc = RecursiveFunc { recFunc: RecursiveFunc ->
        f {
            val c = cache.get(it)
            if (c != null)
                c
            else {
                val res = recFunc.function(recFunc)(it)
                cache.set(it, res)
                res
            }
        }
    }
    return recursiveFunc.function(recursiveFunc)
}

fun fac(me: LongFunc) = { x: Long -> if (x <= 1) 1 else x * me(x - 1) }

fun fib(me: LongFunc) = { x: Long -> if (x <= 2) 1 else me(x - 1) + me(x - 2) }

public class LongCache(private val size: Int = 10) : Cache<Long,Long>(size)

public open class Cache<A,R>(private val size: Int = 10) {

    private val cache = Array<Pair<A, R>?>(size) { null };

    private fun index(arg: A): Int {
        val hash = arg?.hashCode() ?: 0
        return abs(hash) % size
    }

    fun get(arg: A): R? {
        val p = cache[index(arg)]
        if (p != null && p.first == arg)
            return p.second;
        return null;
    }

    fun set(arg: A, res: R) {
        cache[index(arg)] = Pair(arg, res)
    }

}

