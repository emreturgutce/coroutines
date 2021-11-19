package com.emreturgutce

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel

fun main(args: Array<String>) = runBlocking {

    val channel = Channel<Int>()

    val producer = launch {
        delay(500)
        channel.send(1)
    }

    val consumer = launch {
        val data = channel.receive()
        println("Received $data")
    }

    producer.join()
    consumer.join()
}