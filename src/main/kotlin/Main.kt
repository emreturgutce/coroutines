package com.emreturgutce

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update

fun main(args: Array<String>) = runBlocking<Unit> {

    val counter = Counter()

    launch {
        while (true) {
            counter.increment()
            delay(100)
        }
    }

    delay(500)

    launch {
        println("Collector (A) before ${counter.counter.value}")

        counter.counter.collect {
            delay(1000)
            println("Collector (A) $it")
        }
    }
}

class Counter {
    private val _counter = MutableStateFlow(0)

    val counter = _counter.asStateFlow()

    fun increment() {
        _counter.update { it + 1 }
    }
}