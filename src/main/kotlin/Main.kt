package com.emreturgutce

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

fun generateInts() = flow {
    var value = 0

    while (true) {
        delay(1000)
        println("emit: $value")
        emit(value++)
    }
}

fun main(args: Array<String>) = runBlocking<Unit> {

    launch {
        generateInts()
            .filter { it % 2 == 0 }
            .map { "$it * 2" }
            .collect { println("Collected: $it") }
    }
}