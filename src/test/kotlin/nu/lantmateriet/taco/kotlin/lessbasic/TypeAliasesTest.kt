package nu.lantmateriet.taco.kotlin.lessbasic

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

// Typealiases kan endast finnas på "top-level" (men kan vara privata)
typealias IntStringMap = HashMap<Int, String>
typealias SkitDrygTyp = Map<String, Map<Int, List<Map<Double, List<Long>>>>>
typealias IntToStringLambda = (int1: Int, int2: Int) -> String

internal class TypeAliasesTest {

    @Test
    fun `use the typealias IntStringMap`() {
        val map = IntStringMap().apply {
            put(1, "1")
        }

        with(map.entries.first()) {
            assertThat(this.key).isInstanceOf(Integer::class.java)
            assertThat(this.value).isInstanceOf(String::class.java)
        }
    }

    @Test
    fun `use the typealias Lambda`() {
        val func1: (int1: Int, int2: Int) -> String = { int1, int2 -> (int1 + int2).toString() }
        val func2: IntToStringLambda = { int1, int2 -> (int1 + int2).toString() }

        assertThat(func1(7, 3)).isEqualTo(func2(7, 3))
    }
}