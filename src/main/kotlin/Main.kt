package com.emreturgutce

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

fun generateInts() = flow {
    var value = 0

    while (true) {
        delay(3000)
        println("emit: $value")
        emit(value++)
    }
}

fun main(args: Array<String>) = runBlocking<Unit> {

    launch {
        val result = generateInts()
            .drop(1)
            .take(5)
            .fold(1) { a, b ->
                println("a: $a")
                println("b: $b")
                a * b
            }

        println("Result is: $result")
    }
}