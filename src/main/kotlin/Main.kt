import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

fun main() = runBlocking {
    val jobs = arrayListOf<Job>()

    createJobs(jobs)

    jobs.forEach { it.join() }
}

private fun CoroutineScope.createJobs(jobs: ArrayList<Job>) {
    jobs += launch { println("`default`: In thread ${Thread.currentThread().name}") }
    jobs += launch(Dispatchers.Default) { println("`defaultDispatcher`: In thread ${Thread.currentThread().name}") }
    jobs += launch(Dispatchers.Unconfined) { println("`Unconfined`: In thread ${Thread.currentThread().name}") }
    jobs += launch(newSingleThreadContext("newSTC")) { println("`newSTC`: In thread ${Thread.currentThread().name}") }
}
