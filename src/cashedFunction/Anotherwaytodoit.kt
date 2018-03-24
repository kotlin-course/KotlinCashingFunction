package cashedFunction

fun main(args: Array<String>){
    println("Res is: ${fib(92)}")
}

//object Fib : CashingFunctionClass() {

val fib = object : CashingFunctionClass() {
    override fun func(arg: Long): Long {
        return if (arg <= 1) {
            arg
        } else {
            this(arg - 1) + this(arg - 2)
        }
    }
}


open class CashingFunctionClass : LongFunc {
    private val cache = LongCache()
    open fun func(arg: Long):Long {return arg}
    override fun invoke(arg: Long): Long {
        val c = cache.get(arg)
        return if (c != null)
            c
        else {
            val res = func(arg)
            cache.set(arg, res)
            res
        }
    }
}
