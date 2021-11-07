import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.selects.select

fun main() = runBlocking {
    val message = producer()
    val message2 = producer2()

    repeat(15) { println(selector(message, message2)) }
}

fun producer() = GlobalScope.produce {
    send("From Producer 1")
}

fun producer2() = GlobalScope.produce {
    send("From Producer 2")
}

suspend fun selector(message: ReceiveChannel<String>, message2: ReceiveChannel<String>): String {
    return select {
        message.onReceiveCatching { it.getOrNull() ?: "Channel 1 is closed" }
        message2.onReceiveCatching { it.getOrNull() ?: "Channel 2 is closed" }
    }
}