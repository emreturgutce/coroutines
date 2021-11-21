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
        generateInts()
            .take(5)
            .collectLatest {
                println("Collecting $it")
                delay(300)
                println("Collected $it")
            }
    }
}