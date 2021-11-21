package com.emreturgutce

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.util.*
import kotlin.system.measureTimeMillis

fun generateInts() = flow {
    var value = 0

    while (true) {
        delay(100)
        println("emit: $value")
        emit(value++)
    }
}

fun generateRandomInts() = flow {
    var random = Random(100)

    while (true) {
        val value = random.nextInt(20)
        delay(1000)
        println("emit random: $value")
        emit(value)
    }
}

fun main(args: Array<String>) = runBlocking<Unit> {

    var startTime = System.currentTimeMillis()

    val job = launch {
        generateInts()
            .take(5)
            .zip(generateRandomInts()) { a, b -> "$a * $b" }
            .collect { println("zipped at ${System.currentTimeMillis() - startTime}") }
    }

    job.join()

    startTime = System.currentTimeMillis()

    launch {
        generateInts()
            .combine(generateRandomInts()) { a, b -> "$a * $b" }
            .collect { println("combined $it at ${System.currentTimeMillis() - startTime}") }
    }
}