package nu.lantmateriet.taco.kotlin.lessbasic

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import java.time.Instant

/*
In Kotlin, functions are first-class citizen. It means that functions can be assigned to the variables, passed as an arguments or returned from another function.
While Kotlin is statically typed, to make it possible, functions need to have a type.
 */

internal class LambdasTest {

    @Test
    fun `functions as first-class citizens`() {
        fun square(int: Int) = int * int
        // Här lagrar vi RESULTATET av anropet till funktionen square.
        var result = square(4)
        assertThat(result).isEqualTo(16)

        // Här lagrar vi REFERENSEN till funktionen square, vi har alltså INTE anropat funktionen.
        val squareFunc = ::square
        // Här anropar vi funktionen.
        result = squareFunc(9)
        assertThat(result).isEqualTo(81)
    }

    @Test
    fun `function reference`() {
        fun foo() = "foo"
        assertThat(foo()).isEqualTo("foo")

        val fooRef = ::foo
        assertThat(fooRef()).isEqualTo("foo")

        class Foobar {
            fun foo() = "foobar"
        }

        val foobar = Foobar()
        val foobarFooRef = foobar::foo
        assertThat(foobarFooRef()).isEqualTo("foobar")
    }
}


/*
A higher order function is a function that takes a function as an argument, or returns a function.
Higher order function is in contrast to first order functions, which don’t take a function as an argument or return a function as output.
 */

internal class HigherOrderFunctionsTest {

    @Test
    fun `higher-order functions`() {
        // f är en funktion som inte tar någon parameter och inte returnerar något
        fun func1(f: () -> Unit) = f()

        assertThat(func1 { }).isEqualTo(Unit)

        // f är en funktion som tar en Int parameter och returnerar en Int
        fun func2(int: Int, f: (Int) -> Int) = f(int)
        assertThat(func2(3) { it * 2 }).isEqualTo(6)
        assertThat(func2(3) { it * it }).isEqualTo(9)

        // f är en funktion som inte tar någon parameter och returnerar en annan funktion som i sig inte returnerar något.
        fun func3(f: () -> () -> Unit) = f()
        assertThat(func3 { {} }()).isEqualTo(Unit)
    }

    @Test
    fun `calc function`() {
        fun calc(int: Int, f: (int: Int) -> Int) = f(int)

        assertThat(calc(5) { it * 2 }).isEqualTo(10)
        assertThat(calc(5) { it * it }).isEqualTo(25)

        val double: (Int) -> Int = { it * 2 }
        val square: (Int) -> Int = { it * it }
        assertThat(calc(5, double)).isEqualTo(10)
        assertThat(calc(5, square)).isEqualTo(25)
    }

    @Test
    fun `när exekveras en lambda?`() {
        fun createMessage(s: String): String {
            println("${Instant.now()}: Creating message $s")
            return s
        }

        fun log(msg: String) {
            Thread.sleep(200)
            println("${Instant.now()}: $msg")
        }

        log(createMessage("Fiiiiskarna siiiimmar i vaaaattnäääät"))

        fun log(block: () -> String) {
            println("${Instant.now()}: ${block()}")
        }
        log { createMessage("Fååååglarna ääälskar bakom måååålnään") }
    }

    @Test
    fun `runCatching function`() {
        fun divide(taljare: Int, namnare: Int) = taljare / namnare

        assertThat(divide(6, 2)).isEqualTo(3)
        assertThatThrownBy { divide(6, 0) }
            .isInstanceOf(ArithmeticException::class.java)
            .hasMessage("/ by zero")


        // Funktionell programmering ftw!
        var result = runCatching { divide(6, 2) }
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow()).isEqualTo(3)

        result = runCatching { divide(6, 0) }
        assertThat(result.isSuccess).isFalse()
        assertThat(result.exceptionOrNull()).isInstanceOf(ArithmeticException::class.java)
        assertThat(result.exceptionOrNull()).hasMessage("/ by zero")
    }
}