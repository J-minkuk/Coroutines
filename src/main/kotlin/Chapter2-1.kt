import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking

fun main(args: Array<String>) = runBlocking {
    val netDispatcher = newSingleThreadContext(name = "ServiceCall")
    val task = GlobalScope.launch(netDispatcher) {
        println(Thread.currentThread().name)
        doSomething()
    }
    task.join()
}

private fun doSomething() {
    throw UnsupportedOperationException("Can't do")
}
