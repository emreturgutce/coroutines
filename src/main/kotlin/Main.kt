package com.emreturgutce

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach

suspend fun produceNumbers(coroutineScope: CoroutineScope, channel: Channel<Int>, delay: Long) =
    coroutineScope.launch {
        var x: Int = delay.toInt()

        while (true) {
            channel.send(x++)
            delay(delay)
        }
    }

suspend fun consume(coroutineScope: CoroutineScope, producer: ReceiveChannel<Int>) = coroutineScope.launch {
    producer.consumeEach { println("Received value: $it in thread ${Thread.currentThread().name}") }
}

fun main(args: Array<String>) = runBlocking {
    val channel = Channel<Int>()

    produceNumbers(this, channel, 500)
    produceNumbers(this, channel, 200)

    consume(this, channel)

    delay(5000)

    channel.cancel()
}