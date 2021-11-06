import kotlinx.coroutines.*

fun main(args: Array<String>) = runBlocking {
    val job = launch {
        val result = async(coroutineContext) {
            doWork("work 1")
        }
        doWork("work 2")
    }

    job.join()
}

suspend fun doWork(msg: String): Int {
    log("$msg - Working")
    delay(500)
    log("$msg - Work done")
    return 42
}

fun log(msg: String) {
    println("$msg in ${Thread.currentThread().name}")
}
