import kotlin.concurrent.thread

fun main(args: Array<String>) {
    thread {
        Thread.sleep(1000)
        println("World")
    }

    println("Hello, ")

    Thread.sleep(1500)
}