package com.emreturgutce

import kotlinx.coroutines.*
import java.util.concurrent.Executors

val scope = CoroutineScope(Job())
val dispatcher = Executors.newCachedThreadPool().asCoroutineDispatcher()
val executor = Executors.newFixedThreadPool(10).asCoroutineDispatcher()

fun main(args: Array<String>) = runBlocking<Unit> {

    val job1 = scope.launch {
        launch { // 'default' context
            println("\t'default': In thread ${Thread.currentThread().name}")
        }
        launch(Dispatchers.Default) { // 'default' context
            println("\t'defaultDispatcher': In thread ${Thread.currentThread().name}")
        }
        launch(Dispatchers.IO) { // 'IO' context
            println("\t'IO Dispatcher': In thread ${Thread.currentThread().name}")
        }
        launch(Dispatchers.Unconfined) { // Will work with 'main' thread
            println("\t'Unconfined': In thread ${Thread.currentThread().name}")
        }
        launch(newSingleThreadContext("NewThread")) { // Will get new thread
            println("\t'newSTC': In thread ${Thread.currentThread().name}")
        }
        launch(dispatcher) { // Will get dispatched to ForkJoinPool.commonPool (or equivalent)
            println("\t'cachedThreadPool': In thread ${Thread.currentThread().name}")
        }
        launch(executor) { // Will get dispatched to ForkJoinPool.commonPool (or equivalent)
            println("\t'fixedThreadPool': In thread ${Thread.currentThread().name}")
        }
    }

    job1.join()

    println()

    executor.close()
    dispatcher.close()
}