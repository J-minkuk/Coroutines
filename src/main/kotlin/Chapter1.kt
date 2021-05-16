import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

/**
 * 10000개 코루틴 : 1162ms
 * 100000개 코루틴 : 1775ms
 */
object Chapter1_1 {
    @JvmStatic
    fun main(args: Array<String>) = runBlocking {
        println("${Thread.activeCount()} threads active at the start")
        val time = measureTimeMillis { createCoroutines(10) }
        println("${Thread.activeCount()} threads active at end")
        println("Took $time ms")
    }

    private suspend fun createCoroutines(amount: Int) {
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
}

object Chapter1_2 {
    lateinit var user: User

    @JvmStatic
    fun main(args: Array<String>) = runBlocking {
        getUserInfoAsync(1)
        delay(1000L)
        println("User ${user.id}, ${user.name}")
    }

    private suspend fun getUserInfoAsync(id: Int) = GlobalScope.async {
        delay(1000L)
        user = User(id = id, name = "minkuk")
    }

    data class User(
        val id: Int,
        val name: String,
    )
}

object Chapter1_3 {
    @JvmStatic
    fun main(args: Array<String>) = runBlocking {
        val time = measureTimeMillis {
            val name = async { getNameAsync() }
            val age = async { getAgeAsync() }
            println("${name.await()}, ${age.await()}")
        }
        println("Execution took $time ms")
    }

    private suspend fun getNameAsync(): String {
        delay(1000L)
        return "Minkuk"
    }

    private suspend fun getAgeAsync(): Int {
        delay(1000L)
        return 30
    }
}
