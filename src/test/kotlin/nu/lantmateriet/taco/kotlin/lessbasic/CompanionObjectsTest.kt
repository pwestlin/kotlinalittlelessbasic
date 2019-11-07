package nu.lantmateriet.taco.kotlin.lessbasic

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

/*
Companion objects är ett sätt att knyta en funktion (eller en property) till en klass
istället för ett objekt.
Jämför med static i Java.
 */

internal class CompanionObjectsTest {

    data class Person(val first: String, val last: String) {
        companion object Factory {
            fun create(fullName: String): Person {
                with(fullName.split(" ")) {
                    return Person(this[0], this[1])
                }
            }
        }
    }

    @Test
    @Suppress("RedundantCompanionReference")
    fun `skapa person med factory`() {
        assertThat(Person.Factory.create("Mimmi Pigg")).isEqualTo(Person("Mimmi", "Pigg"))
    }

    @Test
    fun `skapa person med factory fast utan att ange factory`() {
        assertThat(Person.create("Mimmi Pigg")).isEqualTo(Person("Mimmi", "Pigg"))
    }

/*
Man kan även lägga till companion objects "utanför klassen, typ som extension functions.
...fast BARA om klassen först deklarerats med "companion object"
data class Person(val first: String, val last: String) {
    companion object
}

 */

    fun Person.Factory.createDonaldDuck() = Person("Donald", "Duck")

    @Test
    fun `Donald Duck`() {
        with(Person.createDonaldDuck()) {
            assertThat(first).isEqualTo("Donald")
            assertThat(last).isEqualTo("Duck")
        }
    }
}
