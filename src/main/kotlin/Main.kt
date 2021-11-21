package com.emreturgutce

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

fun main(args: Array<String>) = runBlocking<Unit> {

    val counter = Counter()

    launch {
        var value = 0

        while (true) {
            counter.produce(value++)
            delay(200)
        }
    }

    launch {
        counter.counter.collect {
            delay(100)
            println("Collected $it")
        }
    }
}

class Counter {
    private val _counter = MutableSharedFlow<Int>(5)

    val counter = _counter.asSharedFlow()

    suspend fun produce(value: Int) {
        _counter.emit(value)
    }
}