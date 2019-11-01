@file:Suppress("SameParameterValue")

package nu.lantmateriet.taco.kotlin.lessbasic

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class DefaultParametersTest {

    private fun fullName(first: String, last: String?): String {
        return if (last == null) first else "$first $last"
    }

    @Test
    fun `namn med efternamn`() {
        assertThat(fullName("Kalle", "Anka")).isEqualTo("Kalle Anka")
    }

    @Test
    fun `namn utan efternamn`() {
        assertThat(fullName("Kalle", null)).isEqualTo("Kalle")
    }

    // Men null är ju inte så kul att skicka in...
    // så vi överlagrar funktionen!
    private fun fullName(first: String): String {
        return fullName(first, null)
    }

    @Test
    fun `namn utan efternamn med överlagrad funktion`() {
        assertThat(fullName("Kalle")).isEqualTo("Kalle")
    }


    // Hmm, finns det något bättre sätt?
    private fun fullNameDefaultParam(first: String, last: String? = null): String {
        return if (last == null) first else "$first $last"
    }

    @Test
    fun `namn med efternamn - default param`() {
        assertThat(fullNameDefaultParam("Kalle", "Anka")).isEqualTo("Kalle Anka")
    }

    @Test
    fun `namn utan efternamn - default param`() {
        assertThat(fullNameDefaultParam("Kalle")).isEqualTo("Kalle")
    }

}

