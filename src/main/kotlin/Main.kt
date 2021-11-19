package com.emreturgutce

import kotlinx.coroutines.*

val scope = CoroutineScope(Job())

fun main(args: Array<String>) = runBlocking {

    val job = scope.launch {
        val deferred = async {
            delay(1000)
            throw Exception()
        }

        try {
            deferred.await()
        } catch (t: Throwable) {
            println("Caught $t")
        }
    }

    job.join()
}