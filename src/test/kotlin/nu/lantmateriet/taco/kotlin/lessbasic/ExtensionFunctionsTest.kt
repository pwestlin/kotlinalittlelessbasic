package nu.lantmateriet.taco.kotlin.lessbasic

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

/*
Extension functions gör att vi kan lägga till ny funktionalitet till befintliga klasser
utan att behöva ärva dem och gör en ny klass.
 */

internal class Person(val first: String, val last: String)

internal class ExtensionFunctionsTest {

    private fun String.reverseAndUpperCase() = this.toUpperCase().reversed()

    @Test
    fun `fram och bak och versaler`() {
        assertThat("Elsa i Paris".reverseAndUpperCase()).isEqualTo("SIRAP I ASLE")
    }

    private fun Person.fullName() = "${this.first} ${this.last}"

    @Test
    fun `full i fan`() {
        assertThat(Person("Gunde", "Svan").fullName()).isEqualTo("Gunde Svan")
    }

    @Test
    fun `många trevliga funktioner på collections är extension functions`() {
        assertThat(
            listOf(1, 2, 3, 4, 5)
                .filter { it % 2 == 0 }
                .map { it.toString() }
        ).containsExactly("2", "4")
    }
}


/*
Vi använder top-level functions bla i domänobjekten:

// Extension method för att konvertera TimeStamp till ZonedDateTime med zon "Z"
fun Timestamp.toZonedDateTime(): ZonedDateTime {
    return ZonedDateTime.ofInstant(this.toInstant(), SYSTEM_ZONE)
}

...och i Valvets JDBC-tester för att få snyggare funktioner för att lagra utbytesobjekt:

/**
 * Lagrar ett utbytesobjekt.
 */
fun <T : Utbytesobjekt> JdbcUtbytesobjektRepository<T>.store(utbytesobjekt: T) {
    this.store(listOf(utbytesobjekt))
}

/**
 * Lagrar utbytesobjekt.
 */
fun <T : Utbytesobjekt> JdbcUtbytesobjektRepository<T>.store(vararg utbytesobjekts: T) {
    this.store(utbytesobjekts.toList())
}


 */


@Suppress("MemberVisibilityCanBePrivate")
internal class ExtensionPropertiesTest {

    val Person.fullName: String
        get() = "${this.first} ${this.last}"

    @Test
    fun `full i fan`() {
        assertThat(Person("Gunde", "Svan").fullName).isEqualTo("Gunde Svan")
    }
}