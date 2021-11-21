package com.emreturgutce

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

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
            .transform {
                if (it % 2 == 0)
                    emit(it * 2)
            }
            .collect { println("Collected: $it") }
    }
}