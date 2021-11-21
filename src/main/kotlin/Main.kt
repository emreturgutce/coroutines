package com.emreturgutce

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

fun generateInts() = flow {
    var value = 0

    while (true) {
        delay(100)
        println("emit: $value")
        emit(value++)
    }
}

fun main(args: Array<String>) = runBlocking<Unit> {

    val flow = generateInts().shareIn(this, SharingStarted.WhileSubscribed())

    launch {
        flow.collect { println("Collector (A) $it") }
    }

    delay(2000)

    launch {
        flow
            .collect { println("Collector (B) $it") }
    }
}