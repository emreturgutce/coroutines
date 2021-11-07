import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.selects.select

fun main() = runBlocking {
    val sideChannel = Channel<Int>()

    launch { sideChannel.consumeEach { println("side $it") } }

    val producer = produceNumbers(sideChannel)

    producer.consumeEach {
        println("$it")
        delay(500)
    }
}

fun produceNumbers(sideChannel: SendChannel<Int>) = GlobalScope.produce<Int> {
    for (num in 1..10) {
        delay(100)
        select<Unit> {
            onSend(num) {}
            sideChannel.onSend(num) {}
        }
    }
    println("Done sending")
}