package com.emreturgutce

import kotlinx.coroutines.*
import java.util.concurrent.Executors

val scope = CoroutineScope(Job())
val dispatcher = Executors.newCachedThreadPool().asCoroutineDispatcher()
val executor = Executors.newFixedThreadPool(10).asCoroutineDispatcher()

fun main(args: Array<String>) = runBlocking<Unit> {

    val job1 = scope.launch {
        launch { // 'default' context
            doWork("default")
        }
        launch(Dispatchers.Default) { // 'default' context
            doWork("defaultDispatcher")
        }
        launch(Dispatchers.IO) { // 'IO' context
            doWork("IO Dispatcher")
        }
        launch(Dispatchers.Unconfined) { // Will work with 'main' thread
            doWork("Unconfined")
        }
        launch(newSingleThreadContext("NewThread")) { // Will get new thread
            doWork("newSTC")
        }
        launch(dispatcher) { // Will get dispatched to ForkJoinPool.commonPool (or equivalent)
            doWork("cachedThreadPool")
        }
        launch(executor) { // Will get dispatched to ForkJoinPool.commonPool (or equivalent)
            doWork("fixedThreadPool")
        }
    }

    job1.join()

    println()

    executor.close()
    dispatcher.close()
}

suspend fun doWork(dispatcherName: String) {
    withContext(Dispatchers.IO) {
        println("\t'$dispatcherName': In thread ${Thread.currentThread().name}")
    }
}