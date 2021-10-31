import kotlinx.coroutines.*

fun main() = runBlocking {
    val job = GlobalScope.launch {
        repeat(1000) {
            if (!isActive) return@launch
            print(".")
            Thread.sleep(100)
        }
    }

    delay(2500)

    job.cancelAndJoin()

    println("done")
}

