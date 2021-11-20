package com.emreturgutce

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel

fun main(args: Array<String>) = runBlocking {
    val channel = Channel<Int> { }
    val jobs = mutableListOf<Job>()

    jobs.add(launch {
        for (x in 1..5) {
            val num = x * x
            println("Sending $num")
            channel.send(num)
        }

        channel.close()
    })

    jobs.add(launch {
        for (value in channel) {
            println("Received $value")
        }
    })

    jobs.forEach { it.join() }
}