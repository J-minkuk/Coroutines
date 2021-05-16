import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking

object Chapter5_1 {
    @JvmStatic
    fun main(args: Array<String>) {
        val iterator: Iterator<Any> = iterator {
            yield(1)
            yield(10L)
            yield("Hello")
        }

        /**
         * 값이 생성된 후 매번 일시중단되어 총 3번 일시중단된다.
         * hasNext() 호출 중에 값이 산출된다.
         */
        while (iterator.hasNext()) {
            println(iterator.next())
        }
    }
}

object Chapter5_2 {
    @JvmStatic
    fun main(args: Array<String>) {
        val sequence = sequence {
            for (i in 0..9) {
                println("Yielding $i")
                yield(i)
            }
        }
        println(sequence.elementAt(1))
        println(sequence.elementAt(2))
        println(sequence.elementAt(3))

        println(sequence.take(5))
        println(sequence.take(5).joinToString())
    }
}

object Chapter5_3 {
    @JvmStatic
    fun main(args: Array<String>) {
        val sequence = sequence {
            yield(1)
            yield(1)
            yield(1)
            yield(2)
            yield(3)
            yield(5)
            yield(13)
        }

        sequence.forEach { print("$it ") }
        println()
        sequence.forEachIndexed { index, value -> println("element at $index is $value") }
    }
}

object Chapter5_4 {
    @JvmStatic
    fun main(args: Array<String>) {
        val fibonacci = sequence<Long> {
            yield(1L)
            var current = 1L
            var next = 1L
            while (true) {
                yield(next)
                val tmpNext = current + next
                current = next
                next = tmpNext
            }
        }
        val indexed = fibonacci.take(50).withIndex()
        for ((index, value) in indexed) println("$index: $value")
    }
}

object Chapter5_5 {
    @JvmStatic
    fun main(args: Array<String>) {
        val fibonacci = iterator<Long> {
            yield(1L)
            var current = 1L
            var next = 1L
            while (true) {
                yield(next)
                val tmpNext = current + next
                current = next
                next = tmpNext
            }
        }
        for (i in 0..10) {
            println("$i is ${fibonacci.next()}")
        }
    }
}

object Chapter5_6 {
    @JvmStatic
    fun main(args: Array<String>) = runBlocking {
        val context = newSingleThreadContext("myThread")
        val producer = GlobalScope.produce(context) {
            send(1)
            send("alpha")
        }

        // 전체 요소 가져오기
//        producer.consumeEach { println(it) }

        // 요소 하나씩 가져오기
        println(producer.receive())
        println(producer.receive())
//        println(producer.receive())
    }
}
