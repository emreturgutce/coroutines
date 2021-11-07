import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel

fun main(args: Array<String>) = runBlocking {
    val channel = Channel<Int>()

    val job = launch {
        for (x in 1..5) {
            println("send: $x")
            channel.send(x)
        }
    }

    println("receive 1: ${channel.receive()}")

    repeat(4) {
        println("receive 2: ${channel.receive()}")
    }

    job.join()
}
