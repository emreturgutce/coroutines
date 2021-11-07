import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce

fun main() = runBlocking {
    val producer = produceNumbers()
    val square = squareNumbers(producer)

    for (i in 1..5) {
        println(square.receive())
    }
}


fun produceNumbers() = GlobalScope.produce {
    var x = 1

    while (true) {
        send(x++)
    }
}

fun squareNumbers(numbers: ReceiveChannel<Int>) = GlobalScope.produce {
    for (x in numbers) {
        send(x * x)
    }
}