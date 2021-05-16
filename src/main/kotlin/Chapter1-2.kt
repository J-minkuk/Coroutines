import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

lateinit var user: User

fun main(args: Array<String>) = runBlocking {
    getUserInfoAsync(1)
    delay(1000L)
    println("User ${user.id}, ${user.name}")
}

suspend fun getUserInfoAsync(id: Int) = GlobalScope.async {
    delay(1000L)
    user = User(id = id, name = "minkuk")
}

data class User(
    val id: Int,
    val name: String,
)
