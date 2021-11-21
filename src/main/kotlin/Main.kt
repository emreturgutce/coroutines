package com.emreturgutce

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.take

fun generateInts() = flow {
    var value = 0

    while (true) {
//        check(value < 2) { "emitter" }
        delay(100)
        emit(value++)
    }
}

fun main(args: Array<String>) = runBlocking<Unit> {

    launch {
        try {
            generateInts()
                .take(5)
                .collect {
                    check(it < 3) { "collector" }
                    println("Collected $it")
                }
        } catch (t: Throwable) {
            println(t.message)
        } finally {
            println("Completed")
        }
    }
}