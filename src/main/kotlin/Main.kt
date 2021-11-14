import kotlinx.coroutines.*
import java.lang.Thread.sleep
import kotlin.concurrent.thread

fun main() {

    GlobalScope.launch {
        delay(1000)

        print("World")
    }

    print("Hello, ")

    sleep(1500)
}

fun old_main() = runBlocking {

    thread {
        sleep(1000)

        println("World")
    }

    print("Hello, ")

    sleep(1500)
}
