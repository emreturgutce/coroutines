package com.emreturgutce

import kotlinx.coroutines.*

fun main(args: Array<String>) = runBlocking<Unit> {

    launch {
        runWithGlobalScope()
        println("returned")
    }
}

suspend fun runWithGlobalScope() {
    coroutineScope {
        launch {
            println("launch 1")

            delay(1000)
        }

        launch {
            println("launch 2")

            delay(2000)
        }
    }

    println("finished")
}
