package nu.lantmateriet.taco.kotlin.lessbasic.anotherpackage

// Så här importerar man en top-level function från ett annat paket
import nu.lantmateriet.taco.kotlin.lessbasic.now
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.Instant

internal class AnotherTopLevelFunctionsTest {

    @Test
    fun `nu ska vara typ nu`() {
        assertThat(now()).isBeforeOrEqualTo(Instant.now())
    }
}