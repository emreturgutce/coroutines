package com.emreturgutce

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.selects.select

fun CoroutineScope.producer1() = produce<String>() {
    var count = 100

    send("from producer 1: ${count++}")
}

fun CoroutineScope.producer2() = produce<String>() {
    var count = 200

    send("from producer 2: ${count++}")
}

fun main(args: Array<String>) = runBlocking {

    val p1 = producer1()
    val p2 = producer2()

    repeat(15) {
        val result = select<String?> {
            p1.onReceiveCatching {
                it.getOrNull()
            }
            p2.onReceiveCatching {
                it.getOrNull()
            }
        }

        println(result)
    }

    println("Finished")

    p1.cancel()
    p2.cancel()
}
