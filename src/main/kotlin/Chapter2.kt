import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking

object Chapter2_1 {
    @JvmStatic
    fun main(args: Array<String>) = runBlocking {
        val netDispatcher = newSingleThreadContext(name = "ServiceCall")
        val task = GlobalScope.launch(netDispatcher) {
            println(Thread.currentThread().name)
            doSomething()
        }
        task.join()
    }
}

object Chapter2_2 {
    /**
     * 실행 결과 출력되는 예외 스택은 없고, Completed까지 찍힌다.
     * async() 블록 안에서 발생하는 예외는 그 결과에 첨부되고, 그 결과를 확인해야 예외를 찾을 수 있다.
     * join()은 예외를 전파하지 않는다.
     */
    @JvmStatic
    fun main(args: Array<String>) = runBlocking {
        val task = GlobalScope.async {
            doSomething()
        }
        task.join()
        println("Completed")
    }
}

object Chapter2_3 {
    /**
     * 예외를 전파하기 위해 Deffered에서 await()을 호출할 수 있다.
     * 예외를 감싸지 않고 전파하는 unwrapping deffered
     */
    @JvmStatic
    fun main(args: Array<String>) = runBlocking {
        val task = GlobalScope.async {
            doSomething()
        }
        task.await()
        println("Success")
    }
}

object Chapter2_4 {
    @JvmStatic
    fun main(args: Array<String>) = runBlocking {
        val task = GlobalScope.launch {
            doSomething()
        }
        task.join()
        println("Success")
    }
}

private fun doSomething() {
    throw UnsupportedOperationException("Can't do")
}
