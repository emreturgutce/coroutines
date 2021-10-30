import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicInteger

fun main() {
    val result = AtomicInteger()

    for (i in 1..1_500_000) {
        GlobalScope.launch {
            result.getAndIncrement()
        }
    }

    Thread.sleep(1000)
    println(result.get())
}