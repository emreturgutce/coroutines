package com.emreturgutce

import kotlinx.coroutines.*

fun main(args: Array<String>) = runBlocking {
    val job = launch {
        try {
            repeat(1000) {
                delay(10)
                print(".")
            }
        } catch (ex: CancellationException) {
            println("Exception!!")
            withContext(NonCancellable) {
                reportError()
            }
        }
    }

    delay(1000)

    job.cancelAndJoin()
}

suspend fun reportError() {
    println("Reporting error")
    try {
        delay(10)
    } catch (t: Throwable) {
        println(t)
    }
    println("Reported error")
}
