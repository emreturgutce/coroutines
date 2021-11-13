import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

const val SEQUENTIAL_THRESHOLD = 20_000_000 / 7

suspend fun compute(array: IntArray, low: Int, high: Int): Long = coroutineScope {
    if (high - low <= SEQUENTIAL_THRESHOLD) {
        (low until high).sumOf { array[it].toLong() }
    } else {
        val mid = low + (high - low) / 2
        val left = async(Dispatchers.Default) { compute(array, low, mid) }
        val right = async(Dispatchers.Default) { compute(array, mid, high) }

        left.await() + right.await()
    }
}

fun main() = runBlocking {

    println("Start")

    Thread.sleep(1000)

    val list = mutableListOf<Int>()

    var limit = 20_000_000

    while (limit > 0) {
        list.add(limit--)
    }

    var result: Long

    measureTimeMillis {
        result = compute(list.toIntArray(), 0, list.toIntArray().size)
    }

    result = 0L

    val time = measureTimeMillis {
        result = compute(list.toIntArray(), 0, list.toIntArray().size)
    }

    println("$result in ${time}ms")
}
