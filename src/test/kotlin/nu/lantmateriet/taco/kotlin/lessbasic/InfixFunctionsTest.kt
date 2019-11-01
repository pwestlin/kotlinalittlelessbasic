package nu.lantmateriet.taco.kotlin.lessbasic

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

/*
Infix-funktioner kan anropas med "infix-notationen" vilket gör att man kan utelämna punkten och parenteserna.
 */

internal class InfixFunctionsTest {

    private data class Person(val name: String, val likes: ArrayList<String> = ArrayList()) {
        infix fun likes(like: String) {
            likes.add(like)
        }
    }

    @Test
    fun `lägg till en like utan infix`() {
        val peter = Person("Peter")
        peter.likes("Kotlin")

        assertThat(peter.likes).containsExactly("Kotlin")
    }

    @Test
    fun `lägg till en like med infix`() {
        val peter = Person("Peter")
        peter likes "Kotlin"

        assertThat(peter.likes).containsExactly("Kotlin")
    }

    @Test
    fun `Kotlins mapOf tar en Pair som parameter som använder infix för "to"`() {
        val map = mapOf(1 to "2", 2 to "3", 42 to "färdtjänst")

        assertThat(map.values).containsExactly("2", "3", "färdtjänst")
    }
}

/*
Infix functions är alltså ett sätt att få kod lite mer som en DSL.

Denna funktionalitet sak man använda väldigt sparsamt!
Används oftast för tester.
 */