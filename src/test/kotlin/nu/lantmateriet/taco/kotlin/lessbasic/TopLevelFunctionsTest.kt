package nu.lantmateriet.taco.kotlin.lessbasic

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.Instant
import java.time.temporal.ChronoUnit


/*
Top-level functions är funktioner som "inte tillhör något objekt".
Tänk hjälpfunktioner.
 */


// Top-level function som är public
fun now(): Instant = Instant.now()

// Top-level function som är private, dvs bara synlig för klasserna i denna fil
private fun notNow() = Instant.now().plus(43, ChronoUnit.DAYS)

internal class TopLevelFunctionsTest {

    @Test
    fun `nu ska vara typ nu`() {
        assertThat(now()).isBeforeOrEqualTo(Instant.now())
    }

    @Test
    fun `inte nu ska definitivt inte vara nu`() {
        assertThat(notNow()).isAfter(Instant.now())
    }
}


/*
Vi använder top-level functions bla i domänobjekten:

/**
 * Skapar en ZonedDateTime med zon "Z"
 */
fun now(): ZonedDateTime = ZonedDateTime.now(SYSTEM_ZONE)

/**
 * Skapar ett LocalDate med zon "Z"
 */
fun today(): LocalDate = LocalDate.now(SYSTEM_ZONE)
 */