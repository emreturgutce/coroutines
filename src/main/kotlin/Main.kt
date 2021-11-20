package com.emreturgutce

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce

suspend fun produceNumbers(coroutineScope: CoroutineScope): ReceiveChannel<Int> =
    coroutineScope.produce {
        var x = 1

        while (true) {
            send(x++)
            delay(500)
        }
    }

suspend fun consume(id: Int, coroutineScope: CoroutineScope, producer: ReceiveChannel<Int>) = coroutineScope.launch {
    producer.consumeEach { println("Received in consumer $id, value: $it in thread ${Thread.currentThread().name}") }
}

suspend fun <T, R> ReceiveChannel<T>.map(
    coroutineScope: CoroutineScope,
    transform: (T) -> R
): ReceiveChannel<R> {
    val receiveChannel = this

    return coroutineScope.produce {
        try {
            for (value in receiveChannel) send(transform(value))
        } catch (ex: CancellationException) {
            receiveChannel.cancel()
        }
    }
}

fun main(args: Array<String>) = runBlocking {
    val producer = produceNumbers(this).map(this) {
        it * it * it
    }

    repeat(5) {
        consume(it, this, producer)
    }

    delay(3000)

    producer.cancel()
}