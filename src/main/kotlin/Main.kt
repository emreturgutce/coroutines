import kotlinx.coroutines.*
import java.lang.Thread.sleep
import java.util.concurrent.atomic.AtomicInteger
import kotlin.concurrent.thread

const val NUM_OF_TASKS = 1_000
const val NUM_OF_LOOPS = 500
const val WAIT_MS = 10L

fun main() = runBlocking {

    println("Starting")

    val result = AtomicInteger()
    val jobs = mutableListOf<Job>()

    for (i in 1..NUM_OF_TASKS) {
        jobs.add(launch(Dispatchers.IO) {
            for (x in 1..NUM_OF_LOOPS) {
                delay(WAIT_MS)
            }
            result.getAndIncrement()
        })
    }

    jobs.forEach { it.join() }
    println(result.get())
}

fun old_main() {

    println("Starting")

    val result = AtomicInteger()
    val threads = mutableListOf<Thread>()

    for (i in 1..NUM_OF_TASKS) {
        threads.add(thread {
            for (x in 1..NUM_OF_LOOPS) {
                sleep(WAIT_MS)
            }
            result.getAndIncrement()
        })
    }

    threads.forEach { it.join() }
    println(result.get())
}
