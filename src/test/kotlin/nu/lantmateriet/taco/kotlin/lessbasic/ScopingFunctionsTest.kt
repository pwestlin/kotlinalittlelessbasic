package nu.lantmateriet.taco.kotlin.lessbasic

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test


/*
Functional-style programming is highly advocated and supported by Kotlin’s syntax as well as a range of functions in Kotlin’s standard library.
Here we will examine five such higher-order functions: apply, with, let, also, and run.

When learning these five functions, you will need to memorize 2 things: how to use them, and when to use them.
Because of their similar nature, they can seem a bit redundant at first.

https://bit.ly/2WB1u6W
 */



@Suppress("RemoveRedundantBackticks", "SimpleRedundantLet")
internal class ScopingFunctionsTest {

    private class Person(var name: String = "Noting", var age: Int = -1) {
        override fun toString() = "$name is $age years young"
    }

    @Test
    fun `apply`() {
        val person = Person().apply {
            name = "Sune"
            age = 51
        }

        assertThat(person.name).isEqualTo("Sune")
        assertThat(person.age).isEqualTo(51)

        /*
        apply() hanterar objektet man opererar på som this
        och returnerar objektet (i vårt fall Person).

        Use the apply() function if you are not accessing any functions of the receiver within your block, and also want to return the same receiver.
        This is most often the case when initializing a new object.
         */
    }

    @Test
    fun `also`() {
        fun createPerson(age: Int): Person = Person(age = age).also {
            // Kontrollera indatat
            require(it.age >= 0) { "age must be >= 0 but was ${it.age}" }

            // Sidoeffekt
            println("age = ${it.age}")
        }

        assertThatThrownBy { createPerson(age = -1) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("age must be >= 0 but was -1")

        /*
        also() hanterar objektet man opererar på som it
        och returnerar objektet (i vårt fall Person).

        Use the also() function, if your block does not access its receiver parameter at all, or if it does not mutate its receiver parameter.
        Don’t use also() if your block needs to return a different value.

        also() is very handy when executing some side effects on an object or validating its data before assigning it to a property.


        Ett exempel på när man kan använda also är när man i ett JDBC-test vill skapa ett objekt, lagra det i DB och sedan tilldela det till en variabel:
        val user = User("foo", "Fo", "O").also { repository.create(it) }
         */
    }

    @Test
    fun `let`() {
        fun createPerson(age: Int?): Person? {
            // Person(age = age) körs endast om age != null, annars returneras null
            return age?.let { Person(age = age) }
        }
        var person: Person? = createPerson(null)
        var result = person?.let {
            it.age
        }
        assertThat(result).isNull()

        person = createPerson(14)
        result = person?.let {
            it.age
        }
        assertThat(result).isEqualTo(14)

        // limit the scope of a single local variable
        val returnValue = Person("Foo", 7).let { p ->
            assertThat(p.name).isEqualTo("Foo")
            assertThat(p.age).isEqualTo(7)
            "fisk"
        }
        assertThat(returnValue).isEqualTo("fisk")

        /*
        let() hanterar objektet man opererar på som it
        och returnerar det som lambdan returnerar (i vårt fall "fisk".

        Use the let() function in either of the following cases:
        -execute code if a given value is not null
        -convert a nullable object to another nullable object
        -limit the scope of a single local variable
         */

    }

    @Test
    fun `with`() {
        val result = with(Person("Sune", 51)) {
            assertThat(name).isEqualTo("Sune")
            assertThat(age).isEqualTo(51)
            assertThat(toString()).isEqualTo("Sune is 51 years young")
            "gris"
        }

        assertThat(result).isEqualTo("gris")

        /*
        with() hanterar objektet man skickar in som parameter som this
        och returnerar det som lambdan returnerar (i vårt fall "gris".

        Use with() only on non-nullable receivers, and when you don’t need its result.
         */
    }

    @Test
    fun `run`() {
        val result = Person("Sune", 51).run {
            println(age)
            "gris"
        }

        assertThat(result).isEqualTo("gris")

        /*
        run() hanterar objektet man opererar på som this
        och returnerar det som lambdan returnerar (i vårt fall "gris".

        Use run() function if you need to compute some value or want to limit the scope of multiple local variables.
        Use run() also if you want to convert explicit parameters to implicit receiver.
         */
    }
}