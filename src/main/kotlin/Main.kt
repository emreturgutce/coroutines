package com.emreturgutce

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

fun generateInts(): Flow<Int> = flow {
    var value = 0

    while (true) {
        delay(1000)
        println("emit $value")
        emit(value++)
    }
}

fun main(args: Array<String>) = runBlocking<Unit> {
    val job = launch {
        generateInts().collect {
            println("Collected (A): $it")
        }
    }

    delay(5500)

    launch {
        generateInts().collect {
            println("Collected (B): $it")
        }
    }

    delay(2500)

    job.cancel()
}