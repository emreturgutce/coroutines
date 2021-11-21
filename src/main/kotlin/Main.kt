package com.emreturgutce

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.util.*

fun generateInts() = flow {
    var value = 0

    while (true) {
        delay(100)
        println("emit: $value")
        emit(value++)
    }
}

fun generateStrings(ndx: Int) = flow {
    emit("$ndx as string-1")
    delay(500)
    emit("$ndx as string-2")
}

@OptIn(FlowPreview::class)
fun main(args: Array<String>) = runBlocking {

    val job = launch {
        generateInts()
            .take(5)
            .flatMapConcat { generateStrings(it) }
            .collect { println("Collected $it") }
    }

    job.join()
}