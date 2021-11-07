import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel

fun main() = runBlocking {
    val channel = Channel<String>()

    launch(coroutineContext) { sendString(channel, "foo", 200L) }
    launch(coroutineContext) { sendString(channel, "bar", 500L) }

    repeat(6) {
        println(channel.receive())
    }

    coroutineContext.cancelChildren()
}

suspend fun sendString(channel: Channel<String>, s: String, interval: Long) {
    while (true) {
        delay(interval)
        channel.send(s)
    }
}