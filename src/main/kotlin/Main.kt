package com.emreturgutce

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel

fun main(args: Array<String>) = runBlocking {
    val channel = Channel<Int>()

    launch { receiveData("First", channel) }
    launch { receiveData("Second", channel) }
    launch { receiveData("Third", channel) }
    launch { receiveData("Fourth", channel) }

    delay(100)

    channel.send(1)
    channel.send(2)
    channel.send(3)
    channel.send(4)
    channel.send(5)
    channel.send(6)

    delay(1000)

    coroutineContext.cancelChildren()
}

suspend fun receiveData(name: String, channel: Channel<Int>) {
    for (value in channel) {
        println("$name: $value")
    }
}