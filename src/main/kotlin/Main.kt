package com.emreturgutce

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce

suspend fun CoroutineScope.produceSquares(): ReceiveChannel<Int> = produce {
    for (x in 1..5) {
        val num = x * x
        println("Sending $num")
        send(num)
    }

}

fun main(args: Array<String>) = runBlocking<Unit> {
    val channel = produceSquares()

    channel.consumeEach { println("Received $it") }
}