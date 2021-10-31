import kotlinx.coroutines.*

fun main() = runBlocking {
    val job = GlobalScope.launch {
        try {
            repeat(1000) {
                yield()
                print(".")
                Thread.sleep(100)
            }
        } catch (ex: CancellationException) {
            println()
            println("Cancelled")
        } finally {
            println()
            println("finally")
        }
    }

    delay(200)

    job.cancelAndJoin()

    println("done")
}

