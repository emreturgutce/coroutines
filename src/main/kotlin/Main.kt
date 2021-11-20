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

suspend fun createSquares(coroutineScope: CoroutineScope, numbersProducer: ReceiveChannel<Int>): ReceiveChannel<Int> =
    coroutineScope.produce {
        for (value in numbersProducer) send(value * value)
    }

suspend fun consume(coroutineScope: CoroutineScope, producer: ReceiveChannel<Int>) = coroutineScope.launch {
    producer.consumeEach { println("Received $it") }
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

    consume(this, producer)

    delay(3000)

    producer.cancel()
}