import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

class TestClient {
    @Test
    fun testAdd() {
        assertEquals(4.0, add(2.0, 2.0))
        assertEquals(0.0, add(0.0, 0.0))
    }

    @Test
    fun testSub() {
        assertEquals(0.0, sub(2.0, 2.0))
        assertEquals(2.0, sub(5.0, 3.0))
        assertEquals(-2.0, sub(3.0, 5.0))
    }

    @Test
    fun testDivide() {
        assertEquals(0.25, divide(1.0, 4.0))
        assertEquals(1.0, divide(4.0, 4.0))
    }

    @Test
    fun testDivideBy0() {
        assertFails {
            divide(1.0, 0.0)
        }
        assertFails {
            divide(3.0, 0.0)
        }
    }

    @Test
    fun testMultiply() {
        assertEquals(1.0, multiply(0.25, 4.0))
        assertEquals(12.0, multiply(6.0, 2.0))
    }

    @Test
    fun testDispatch(){
        assertEquals("4", dispatch("2.0 + 2.0"))
        assertEquals("0", dispatch("2.0 - 2.0"))
        assertEquals("4", dispatch("2.0 * 2.0"))
        assertEquals("1", dispatch("2.0 / 2.0"))
        //Todo add this test assertEquals("Error", dispatch("1 / 0"))
    }
}
