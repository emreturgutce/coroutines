import kotlinx.coroutines.*

fun main() = runBlocking {
    var job = withTimeoutOrNull(100) {
        repeat(1000) {
            yield()
            print(".")
            Thread.sleep(100)
        }
    }

    if (job == null) {
        println("timed out")
    }

    delay(200)

    println("done")
}

