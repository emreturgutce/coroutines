package com.emreturgutce

import kotlinx.coroutines.*

fun main(args: Array<String>) = runBlocking {
    val job = async {
        delay(100)
        42
    }
    job.cancelAndJoin()

    if (!job.isCancelled) {
        val result = job.await()
    } else {
        println("Already cancelled")
    }

}
