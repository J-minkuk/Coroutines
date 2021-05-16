import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

/**
 * 10000개 코루틴 : 1162ms
 * 10000개 코루틴 : 1775ms
 */
fun main(args: Array<String>) = runBlocking {
    println("${Thread.activeCount()} threads active at the start")
    val time = measureTimeMillis { createCoroutines(10) }
    println("${Thread.activeCount()} threads active at end")
    println("Took $time ms")
}

suspend fun createCoroutines(amount: Int) {
    val jobs = ArrayList<Job>()
    for (i in 1..amount) {
        jobs += GlobalScope.launch {
            println("Started $i in ${Thread.currentThread().name}")
            delay(1000L)
            println("Finished $i in ${Thread.currentThread().name}")
        }
    }
    jobs.forEach { it.join() }
}
