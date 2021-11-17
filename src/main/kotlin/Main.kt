package com.emreturgutce

import kotlinx.coroutines.*

fun main(args: Array<String>) = runBlocking<Unit> {
    val result: Deferred<Int> = doWorkAsync("Work1")

    val answer = result.await()

    println("The answer is $answer")

    val res1: Int = doWork("Work2")
    val res2: Deferred<Int> = async { doWork("Work3") }

    res2.await()
}

fun doWorkAsync(msg: String): Deferred<Int> = GlobalScope.async {
    log("$msg - Working")
    delay(300)
    log("$msg - Done")

    return@async 42
}

suspend fun doWork(msg: String): Int {
    log("$msg - Working")
    delay(300)
    log("$msg - Done")

    return 42
}

fun log(msg: String) {
    println("$msg in ${Thread.currentThread().name}")
}
