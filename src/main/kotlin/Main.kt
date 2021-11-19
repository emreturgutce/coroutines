package com.emreturgutce

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

fun generateInts() = flow<Int> {
    repeat(10) {
        delay(1000)
        emit(it)
    }
}

fun main(args: Array<String>) = runBlocking {

    generateInts().collect {
        println("Collected: $it")
    }
}