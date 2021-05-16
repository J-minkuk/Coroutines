import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking

object Chapter4_1 {
    @JvmStatic
    fun main(args: Array<String>) = runBlocking {
        val client: ProfileServiceRepository = ProfileServiceClient()
        val profile = client.fetchByIdAsync(1).await()
        println(profile)
    }

    interface ProfileServiceRepository {
        fun fetchByNameAsync(name: String): Deferred<Profile>
        fun fetchByIdAsync(id: Int): Deferred<Profile>
    }

    class ProfileServiceClient : ProfileServiceRepository {
        override fun fetchByNameAsync(name: String) = GlobalScope.async {
            Profile(1, name)
        }

        override fun fetchByIdAsync(id: Int) = GlobalScope.async {
            Profile(id, "Minkuk")
        }
    }
}

object Chapter4_2 {
    @JvmStatic
    fun main(args: Array<String>) = runBlocking {
        val client: ProfileServiceRepository = ProfileServiceClient()
        val profile = client.fetchById(1)
        println(profile)
    }

    interface ProfileServiceRepository {
        suspend fun fetchByName(name: String): Profile
        suspend fun fetchById(id: Int): Profile
    }

    class ProfileServiceClient : ProfileServiceRepository {
        override suspend fun fetchByName(name: String) = Profile(1, name)

        override suspend fun fetchById(id: Int) = Profile(id, "Minkuk")
    }
}

data class Profile(val id: Int, val name: String)

/**
 * 컨텍스트 조합
 */
object Chapter4_3 {
    @JvmStatic
    fun main(args: Array<String>) = runBlocking {
        val dispatcher = newSingleThreadContext("myDispatcher")
        val handler = CoroutineExceptionHandler { _, throwable ->
            println("Error captured")
            println("Message: ${throwable.message}")
        }

        val context = dispatcher + handler
        GlobalScope.launch(context) {
            println("Running in ${Thread.currentThread().name}")
        }.join()
    }
}

/**
 * 컨텍스트 분리
 */
object Chapter4_4 {
    @JvmStatic
    fun main(args: Array<String>) = runBlocking {
        val dispatcher = newSingleThreadContext("myDispatcher")
        val handler = CoroutineExceptionHandler { _, throwable ->
            println("Error captured")
            println("Message: ${throwable.message}")
        }

        val context = dispatcher + handler
        val tmpCtx = context.minusKey(dispatcher.key)
        GlobalScope.launch(tmpCtx) {
            println("Running in ${Thread.currentThread().name}")
        }.join()
    }
}

object Chapter4_5 {
    @JvmStatic
    fun main(args: Array<String>) = runBlocking {
        val handler = CoroutineExceptionHandler { context, throwable ->
            println("Error captured in $context")
            println("Message: ${throwable.message}")
        }

        GlobalScope.launch(handler) {
            println("Running in ${Thread.currentThread().name}")
//            throw UnsupportedOperationException("Can't do")
        }.join()
    }
}
