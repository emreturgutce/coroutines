package com.emreturgutce

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import java.util.*

const val TOTAL_WORK_ITEMS = 200
const val TOTAL_AGENTS = 10

data class Work(
    val x: Long,
    val y: Long,
    var total: Long = 0L
)

suspend fun agent(input: ReceiveChannel<Work>, output: SendChannel<Work>) {
    for (work in input) {
        work.total = work.x * work.y
        delay(work.total)
        output.send(work)
    }
}

suspend fun producer(output: SendChannel<Work>) {
    repeat(TOTAL_WORK_ITEMS) {
        output.send(Work((0L..100).random(), (0L..100).random()))
    }
}

suspend fun consumer(input: ReceiveChannel<Work>) {
    for (work in input) {
        println("${work.x}*${work.y} = ${work.total}")
    }
}

suspend fun CoroutineScope.run() {
    val producerChannel = Channel<Work>()
    val consumerChannel = Channel<Work>()

    repeat(TOTAL_AGENTS) {
        launch {
            agent(producerChannel, consumerChannel)
        }
    }

    launch { producer(producerChannel) }
    launch { consumer(consumerChannel) }
}

fun main(args: Array<String>) = runBlocking {
    run()
}

private object RandomRangeSingleton : Random()

fun ClosedRange<Long>.random() = (RandomRangeSingleton.nextInt((endInclusive.toInt() + 1) - start.toInt()) + start)
