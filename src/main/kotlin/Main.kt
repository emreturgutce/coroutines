import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce

fun main() = runBlocking {
    val producer = produceNumbers()
    repeat(5) {
        consumer(it, producer)
    }
    println("Launched")
    delay(150)
    producer.cancel()
}

fun produceNumbers() = GlobalScope.produce {
    var x = 1

    while (true) {
        send(x++)
    }
}

fun consumer(id: Int, channel: ReceiveChannel<Int>) = GlobalScope.launch {
    channel.consumeEach {
        println("Processor #$id received $it in thread ${Thread.currentThread().name}")
    }
}