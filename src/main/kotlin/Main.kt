package com.emreturgutce

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.selects.select

fun CoroutineScope.produceNumbers() = produce<Int>() {
    var num = 0

    while (true) {
        delay(5000)
        send(num++)
    }
}

fun main(args: Array<String>) = runBlocking<Unit> {

    val producer = produceNumbers()

    select {
        producer.onReceive {
            println("Consumed: $it")
        }

        onTimeout(1000) {
            println("Timed out")
        }
    }
}