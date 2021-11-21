package com.emreturgutce

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.system.measureTimeMillis

fun generateInts() = flow {
    var value = 0

    while (true) {
        delay(100)
        println("emit: $value")
        emit(value++)
    }
}

fun main(args: Array<String>) = runBlocking<Unit> {

    launch {
        val time = measureTimeMillis {
            generateInts()
                .take(5)
                .buffer(5)
                .collect {
                    delay(500)
                    println("Collected $it")
                }
        }

        println("Took ${time}ms")
    }
}