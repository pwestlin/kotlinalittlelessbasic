package nu.lantmateriet.taco.kotlin.lessbasic

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class OperatorOverloadingTest {

    @Test
    fun `plusa tre listor`() {
        val list1 = listOf("Göran", "Persson")
        val list2 = listOf("bor", "inte")
        val list3 = listOf("här", "längre")

        assertThat(list1 + list2 + list3).containsExactly("Göran", "Persson", "bor", "inte", "här", "längre")
    }

    @Test
    fun `egen klass`() {
        data class MyInt(val int: Int) {
            operator fun minus(myInt: MyInt): MyInt {
                return MyInt(int - myInt.int)
            }
        }

        val myInt7 = MyInt(7)
        val myInt3 = MyInt(3)
        assertThat((myInt7 - myInt3).int).isEqualTo(4)
    }
}