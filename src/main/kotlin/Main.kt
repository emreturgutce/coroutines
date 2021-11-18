package com.emreturgutce

import kotlinx.coroutines.*

fun main(args: Array<String>) = runBlocking {
    var child1: Job? = null

    coroutineScope {
        val job = GlobalScope.launch {
            child1 = launch {
                repeat(1000) {
                    Thread.sleep(1000)
                    print("1")
                    yield()
                }
            }

            repeat(1000) {
                delay(1000)
                println("0")
            }
        }

        delay(4000)
        child1?.cancelAndJoin()
        println()
        println("job is cancelled: ${job.isCancelled}")
        println("job is active: ${job.isActive}")

        job.join()
    }

    println("coroutineScope finished")
}
