import kotlinx.browser.document
import kotlinx.dom.addClass
import kotlinx.html.classes
import kotlinx.html.div
import kotlinx.html.dom.append
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
    fun testDispatch() {
        assertEquals("4", dispatch("2 + 2"))
        assertEquals("0", dispatch("2.0 - 2.0"))
        assertEquals("4", dispatch("2.0 * 2.0"))
        assertEquals("1", dispatch("2.0 / 2.0"))
        assertEquals("Error", dispatch("1 / 0"))
        assertEquals("0.2", dispatch("0.1 + .1"))
        assertEquals("12", dispatch("12 +"))
    }

    @Test
    fun testAddCharToOperation() {
        document.body?.append {
            div {
                +"0"
                classes = setOf("prompt-box")
            }
        }
        val prompt = document.querySelector(".prompt-box")!!

        assertEquals("", operation, "operation 0")
        assertEquals("0", prompt.textContent, "prompt 0")

        addCharToOperations('1')
        assertEquals("1", operation, "operation 1")
        assertEquals("1", prompt.textContent, "prompt 1")

        addCharToOperations('.')
        assertEquals("1.", operation, "operation 2")
        assertEquals("1.", prompt.textContent, "prompt 2")

        addCharToOperations('5')
        assertEquals("1.5", operation, "operation 3")
        assertEquals("1.5", prompt.textContent, "prompt 3")

        addCharToOperations('.')
        assertEquals("1.5", operation, "operation 4")
        assertEquals("1.5", prompt.textContent, "prompt 4")

        addCharToOperations('+')
        assertEquals("1.5 +", operation, "operation 5")
        assertEquals("+", prompt.textContent, "prompt 5")

        addCharToOperations('*')
        assertEquals("1.5 *", operation, "operation 6")
        assertEquals("*", prompt.textContent, "prompt 6")

        addCharToOperations('2')
        assertEquals("1.5 * 2", operation, "operation 7")
        assertEquals("2", prompt.textContent, "prompt 7")

        addCharToOperations('/')
        assertEquals("3 /", operation, "operation 8")
        assertEquals("/", prompt.textContent, "prompt 8")

        addCharToOperations('0')
        assertEquals("3 / 0", operation, "operation 9")
        assertEquals("0", prompt.textContent, "prompt 9")

        addCharToOperations('+')
        assertEquals("", operation, "operation 10")
        assertEquals("Error", prompt.textContent, "prompt 10")

        addCharToOperations('1')
        assertEquals("1", operation, "operation 11")
        assertEquals("1", prompt.textContent, "prompt 11")
    }

    @Test
    fun testBackward(){
        document.body?.append {
            div {
                +"0"
                classes = setOf("prompt-box")
            }
        }
        val prompt = document.querySelector(".prompt-box")!!


        operation = "12 + 24"

        removeLast()
        assertEquals("2", prompt.textContent, "12 + 24")

        removeLast()
        assertEquals("+", prompt.textContent, "12 + 2")

        removeLast()
        assertEquals("12", prompt.textContent, "12 + ")

        removeLast()
        assertEquals("1", prompt.textContent, "12")
    }
}
