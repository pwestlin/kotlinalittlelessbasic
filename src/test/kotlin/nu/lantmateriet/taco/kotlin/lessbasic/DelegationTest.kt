@file:Suppress("MemberVisibilityCanBePrivate")

package nu.lantmateriet.taco.kotlin.lessbasic

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import kotlin.properties.Delegates

/*
Delegation är när man delegerar viss funktionalitet till ett annat objekt.
 */

internal class DelegationTest {

    @Test
    fun `lazy delegation`() {
        data class Person(val first: String, val last: String) {
            val fullName: String by lazy {
                println("Nu initierar vi fullName")
                "$first $last"
            }
        }

        Person("Kalle", "Kula").let { person ->
            assertThat(person.fullName).isEqualTo("Kalle Kula")
            assertThat(person.fullName).isEqualTo("Kalle Kula")
        }
    }

    @Test
    fun `notNull delegation`() {
        data class Person(val first: String, val last: String) {
            var age: Int by Delegates.notNull()
        }

        assertThatThrownBy { Person("Kalle", "Kula").age }
            .isInstanceOf(IllegalStateException::class.java)
            .hasMessage("Property age should be initialized before get.")

        Person("Kalle", "Kula").let { person ->
            person.age = 29
            assertThat(person.age).isEqualTo(29)
        }


        class Foo {
            lateinit var bar: String
        }

        assertThatThrownBy { Foo().bar }
            .isInstanceOf(UninitializedPropertyAccessException::class.java)
            .hasMessage("lateinit property bar has not been initialized")
        Foo().let { foo ->
            foo.bar = "färdtjänst"
            assertThat(foo.bar).isEqualTo("färdtjänst")
        }

        /*
        Delegates.notNull och lateinit var verkar vara typ samma sak.
        Vilken ska jag använda och när?

        Det korta svaret:
        lateinit var

        Det mindre korta svaret:
        När variabeln du vill kontrollera är publik använder du lateinit var.
        Om variabeln sätts av en funktion så använder du Delegates.notNull.
         */
        class Foobar {
            private lateinit var bar: String

            fun doSomethingThatReadsBar() {
                println(bar)
            }

            fun doSomethingThatSetsBar() {
                bar = "fiskmås"
            }
        }

        assertThatThrownBy { Foobar().doSomethingThatReadsBar() }
            .isInstanceOf(UninitializedPropertyAccessException::class.java)
            .hasMessage("lateinit property bar has not been initialized")
        Foobar().apply {
            doSomethingThatSetsBar()
            doSomethingThatReadsBar()
        }
    }

    @Test
    fun `observable delegation`() {
        data class Person(val first: String, val last: String) {
            var age: Int by Delegates.observable(0) { property, oldValue, newValue ->
                println("$property has changed from $oldValue to $newValue")
                prevAge = oldValue
            }
            var prevAge = -1
        }

        Person("Kalle", "Kula").let { person ->
            assertThat(person.age).isEqualTo(0)
            assertThat(person.prevAge).isEqualTo(-1)
            person.age = 47
            assertThat(person.age).isEqualTo(47)
            assertThat(person.prevAge).isEqualTo(0)
        }
    }

    @Test
    fun `vetoable delegation`() {
        data class Person(val first: String, val last: String) {
            var age: Int by Delegates.vetoable(0) { property, oldValue, newValue ->
                if (newValue in 1..150) {
                    println("$property has changed from $oldValue to $newValue")
                    prevAge = oldValue
                    true
                } else {
                    println("$property has not been changed and therefore is still $oldValue")
                    false
                }
            }
            var prevAge = -1
        }

        Person("Kalle", "Kula").let { person ->
            assertThat(person.age).isEqualTo(0)
            assertThat(person.prevAge).isEqualTo(-1)

            person.age = 47
            assertThat(person.age).isEqualTo(47)
            assertThat(person.prevAge).isEqualTo(0)

            person.age = 151
            assertThat(person.age).isEqualTo(47)
            assertThat(person.prevAge).isEqualTo(0)
        }
    }

    private interface Base {
        fun print()
    }

    private class BaseImpl(val x: Int) : Base {
        override fun print() {
            print(x)
        }
    }

    private class Derived(b: Base) : Base by b

    @Test
    fun `favor composition over inheritance`() {
        val base: Base = BaseImpl(10)
        Derived(base).print()

    }


    private interface Person {
        val name: String
    }

    private class PersonImpl(override val name: String) : Person

    private interface Parent : Person {
        val children: List<Person>
    }

    private class ParentImpl(person: Person, override val children: List<Person>) : Parent, Person by person

    private interface SuperHero {
        val heroName: String
    }

    private class SuperHeroImpl(override val heroName: String) : SuperHero

    private class SuperHeroAndParent(parent: Parent, superHero: SuperHero) : Parent by parent, SuperHero by superHero

    @Test
    fun `multiple inheritance`() {
        val child: Person = PersonImpl("Jag är ett litet, litet barn")
        val adult: Person = PersonImpl("Jag är en stor vuxen")
        val parent: Parent = ParentImpl(adult, listOf(child))
        val superHero: SuperHero = SuperHeroImpl("SuperMom")
        val superHeroAndParent = SuperHeroAndParent(parent, superHero)

        with(superHeroAndParent) {
            assertThat(this.name).isEqualTo(adult.name)
            assertThat(this.children).isEqualTo(parent.children)
            assertThat(this.heroName).isEqualTo(superHero.heroName)
        }
    }
}