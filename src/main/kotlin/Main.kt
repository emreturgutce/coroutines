package com.emreturgutce

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.selects.select

fun CoroutineScope.producer1() = produce<String>() {
    var count = 100

    while (true) {
        send("from producer 1: ${count++}")
    }
}

fun CoroutineScope.producer2() = produce<String>() {
    var count = 200

    while (true) {
        send("from producer 2: ${count++}")
    }
}

fun main(args: Array<String>) = runBlocking {

    val p1 = producer1()
    val p2 = producer2()

    repeat(15) {
        select<Unit> {
            p1.onReceive(::println)
            p2.onReceive(::println)
        }
    }

    println("Finished")

    p1.cancel()
    p2.cancel()
}
