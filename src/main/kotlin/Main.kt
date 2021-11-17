package com.emreturgutce

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

class Person {
    val children = GlobalScope.async(start = CoroutineStart.LAZY) { loadChildren() }

    companion object {
        suspend fun loadChildren(): List<String> {
            println("Loading children")
            Thread.sleep(4000)
            return listOf("Harry", "Sam", "Alex")
        }
    }
}

fun main(args: Array<String>) = runBlocking {
    println("Creating person")

    val kevin = Person()
    kevin.children.start()

    Thread.sleep(2000)

    val time = measureTimeMillis {
        kevin.children.await().forEach(::println)
    }
    println("Person created in ${time}ms")
}
