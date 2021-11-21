package com.emreturgutce

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

fun generateInts() = flow {
    var value = 0

    while (true) {
        delay(500)
        println("emit $value")
        emit(value++)
    }
}

fun main(args: Array<String>) = runBlocking<Unit> {

    val flow = generateInts().stateIn(this)

    launch {
        flow.collect { println("Collector (A) $it") }
    }

    delay(2000)

    launch {
        println("Collector (B) before collect ${flow.value}")
        flow.collect { println("Collector (B) $it") }
    }
}