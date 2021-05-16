import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

fun main(args: Array<String>) = runBlocking {
    val time = measureTimeMillis {
        val name = async { getNameAsync() }
        val age = async { getAgeAsync() }
        println("${name.await()}, ${age.await()}")
    }
    println("Execution took $time ms")
}

suspend fun getNameAsync(): String {
    delay(1000L)
    return "Minkuk"
}

suspend fun getAgeAsync(): Int {
    delay(1000L)
    return 30
}
