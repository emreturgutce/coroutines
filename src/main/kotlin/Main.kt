package com.emreturgutce

import kotlinx.coroutines.*

fun main(args: Array<String>) = runBlocking {

    coroutineScope {
        val job = launch {
            launch { doWork(1) }
            launch { doWork(2) }

            launch {
                delay(2000)
                throw Exception("Some Exception")
            }
        }
    }

    println("Scope ended")
}

suspend fun doWork(i: Int) {
    while (true) {
        print(i)
        delay(200)
    }
}