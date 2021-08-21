import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.html.div
import kotlinx.html.dom.append
import org.w3c.dom.Node

fun main() {
    window.onload = { document.body?.sayHello() }
}

fun Node.sayHello() {
    append {
        div {
            +"Hello from JS"
        }
    }
}

fun dispatch(operation: String): String {
    var amount = "Error"
    val x = 2.0 //Todo parse operation before sign
    val y = 2.0 //todo parse operation after sign

    if (operation.contains("+")) amount = add(x, y).toString()
    else if (operation.contains("-")) amount = sub(x, y).toString()
    else if (operation.contains("*")) amount = multiply(x, y).toString()
    else if (operation.contains("/") && y != 0.0) amount = divide(x, y).toString()

    return amount
}

fun add(x: Double, y: Double): Double = x + y
fun sub(x: Double, y: Double): Double = x - y
fun multiply(x: Double, y: Double): Double = x * y
fun divide(x: Double, y: Double): Double = if (y == 0.0) throw ArithmeticException("Division by 0 is not allowed") else x / y