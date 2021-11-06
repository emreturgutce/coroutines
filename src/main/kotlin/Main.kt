import kotlinx.coroutines.*
import kotlin.random.Random
import kotlin.system.measureTimeMillis

fun main(args: Array<String>) = runBlocking {
    val job = launch {
        val time = measureTimeMillis {
            val r1 = async { doWorkOne() }
            val r2 = async { doWorkTwo() }
            println("result: ${r1.await() + r2.await()}")
        }
        println("Done in: $time")
    }

    job.join()
}

suspend fun doWorkOne(): Int {
    delay(100)
    println("Working 1")
    return Random(System.currentTimeMillis()).nextInt(42)
}

suspend fun doWorkTwo(): Int {
    delay(200)
    println("Working 2")
    return Random(System.currentTimeMillis()).nextInt(42)
}
