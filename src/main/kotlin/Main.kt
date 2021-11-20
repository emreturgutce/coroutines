package com.emreturgutce

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.selects.select

fun CoroutineScope.produceNumbers(side: SendChannel<Int>) = produce<Int>() {
    for (num in 1..10) {
        delay(100)
        select<Unit> {
            onSend(num) {}
            side.onSend(num) {}
        }
    }

    println("Done sending")
}

fun main(args: Array<String>) = runBlocking {

    val side = Channel<Int>()

    launch { side.consumeEach { println("Side Channel Consumed: $it") } }

    val producer = produceNumbers(side)

    producer.consumeEach {
        delay(400)
        println(it)
    }
}