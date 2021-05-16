import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.concurrent.CancellationException
import kotlin.coroutines.CoroutineContext

object Chapter3_1 {
    @JvmStatic
    fun main(args: Array<String>) = runBlocking {
        GlobalScope.launch {
            throw UnsupportedOperationException("Can't do")
        }

        delay(1000L)
        println("Finished")
    }
}

object Chapter3_2 {
    @JvmStatic
    fun main(args: Array<String>) = runBlocking {
        GlobalScope.launch(start = CoroutineStart.LAZY) {
            throw UnsupportedOperationException("Can't do")
        }

        delay(1000L)
        println("Finished")
    }
}

/**
 * Finished만 출력
 */
object Chapter3_3 {
    @JvmStatic
    fun main(args: Array<String>) = runBlocking {
        val job = GlobalScope.launch(start = CoroutineStart.LAZY) {
            delay(3000L)
            println("Delay Finished")
        }

        job.start()
        println("Finished")
    }
}

/**
 * Delay Finished
 * Finished
 * 출력
 */
object Chapter3_4 {
    @JvmStatic
    fun main(args: Array<String>) = runBlocking {
        val job = GlobalScope.launch(start = CoroutineStart.LAZY) {
            delay(3000L)
            println("Delay Finished")
        }

        job.join()
        println("Finished")
    }
}

/**
 * cancel이 먼저되면 예외가 발생하지 않아 exception handler가 동작하지 않는다.
 * exception handler가 동작해도 invokeOnCompletion은 동작한다.
 */
object Chapter3_5 {
    @JvmStatic
    fun main(args: Array<String>) = runBlocking {
        val exceptionHandler = CoroutineExceptionHandler { _: CoroutineContext, throwable: Throwable ->
            println("coroutine exception handler job cancelled due to ${throwable.message}")
        }

        val job = GlobalScope.launch(exceptionHandler) {
//            delay(5000L)
            throw UnsupportedOperationException("can't do")
        }

        job.invokeOnCompletion { handler ->
            handler?.let {
                println("invoke on completion Job cancelled due to ${it.message}")
            }
        }

        println("before delay 2000")
        delay(2000L)
        println("for cancel job")

        job.cancel(cause = CancellationException("Timeout"))

        job.join()
    }
}
