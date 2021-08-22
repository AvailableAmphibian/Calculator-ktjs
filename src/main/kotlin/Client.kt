import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.events.KeyboardEvent

var operation: String = ""
val prompt get() = document.querySelector(".prompt-box")!!
private var shiftPressed: Boolean = false
fun main() {
    window.onload = { initButtons() }
}

fun initButtons() {
    document.body?.addEventListener("keydown", { keyPressed(it as KeyboardEvent) })

    //Digit buttons
    document.querySelector(".decimal")?.addEventListener("click", { addCharToOperations('.') })
    document.querySelector(".digit-1")?.addEventListener("click", { addCharToOperations('1') })
    document.querySelector(".digit-2")?.addEventListener("click", { addCharToOperations('2') })
    document.querySelector(".digit-3")?.addEventListener("click", { addCharToOperations('3') })
    document.querySelector(".digit-4")?.addEventListener("click", { addCharToOperations('4') })
    document.querySelector(".digit-5")?.addEventListener("click", { addCharToOperations('5') })
    document.querySelector(".digit-6")?.addEventListener("click", { addCharToOperations('6') })
    document.querySelector(".digit-7")?.addEventListener("click", { addCharToOperations('7') })
    document.querySelector(".digit-8")?.addEventListener("click", { addCharToOperations('8') })
    document.querySelector(".digit-9")?.addEventListener("click", { addCharToOperations('9') })
    document.querySelector(".digit-0")?.addEventListener("click", { addCharToOperations('0') })

    //Operator buttons
    document.querySelector(".add")?.addEventListener("click", { addCharToOperations('+') })
    document.querySelector(".sub")?.addEventListener("click", { addCharToOperations('-') })
    document.querySelector(".multiply")?.addEventListener("click", { addCharToOperations('*') })
    document.querySelector(".divide")?.addEventListener("click", { addCharToOperations('/') })
    document.querySelector(".equal")?.addEventListener("click", { validate() })

    //Cleaners
    document.querySelector(".backward")?.addEventListener("click", { removeLast() })
    document.querySelector(".clean")?.addEventListener("click", { clean() })
}

fun keyPressed(keyEvent: KeyboardEvent) {
    when (keyEvent.key) {
        "0" -> addCharToOperations('0')
        "1" -> addCharToOperations('1')
        "2" -> addCharToOperations('2')
        "3" -> addCharToOperations('3')
        "4" -> addCharToOperations('4')
        "5" -> addCharToOperations('5')
        "6" -> addCharToOperations('6')
        "7" -> addCharToOperations('7')
        "8" -> addCharToOperations('8')
        "9" -> addCharToOperations('9')

        "*" -> addCharToOperations('*')
        "+" -> addCharToOperations('+')
        "-" -> addCharToOperations('-')
        "/" -> addCharToOperations('/')

        "Backspace" -> removeLast()
        "Enter" -> validate()
        "Delete" -> clean()
    }
}

/**
 * The main work is done here, every check is done here, and it is dispatched here
 */
fun addCharToOperations(c: Char) {
    if (prompt.textContent == "0" || prompt.textContent == "0 " || prompt.textContent == "Error" || operation == "") {
        prompt.textContent = ""
    }

    if (c in '0'..'9')
        addDigit(c.digitToInt())
    if (c == '.')
        addDot()

    if (isOperator(c)) {
        if (operationHasCouple()) {
            operation = dispatch(operation)
            if (operation == "Error") {
                operation = ""
                prompt.textContent = "Error"
                return
            }
        }

        operation = if (promptIsAnOperator()) "${operation.dropLast(1)}$c" else "$operation $c"
        prompt.textContent = c.toString()
    }
}

fun removeLast() {
    val last = operation.drop(operation.lastIndex)
    operation = operation.dropLast(if (last == " ") 2 else 1)

    val splits = operation.split(" ")
    prompt.textContent = if (splits.last() == "") splits[splits.lastIndex - 1] else splits.last()
}

fun clean() {
    prompt.textContent = "0"
    operation = ""
}

fun validate() {
    prompt.textContent = dispatch(operation)
    operation = ""
}

fun addDigit(i: Int) {
    addSpaceIfNecessary()
    operation = "$operation$i"
    prompt.textContent = if (promptIsAnOperator()) i.toString() else "${prompt.textContent}$i"
}

fun addDot() {
    if (prompt.textContent!!.contains('.')) return
    addSpaceIfNecessary()

    operation = "$operation."
    prompt.textContent = "${prompt.textContent}."
}

fun addSpaceIfNecessary() {
    if (promptIsAnOperator())
        operation = "$operation "
}


fun operationHasCouple() = operation.matches(Regex("^\\d+(\\.\\d+)? ([+\\-*\\/]) \\d+(\\.\\d+)?$"))

private fun isOperator(c: Char): Boolean = c == '+' || c == '-' || c == '*' || c == '/'

/**
 * Verify if the prompt contains only an operator
 */
private fun promptIsAnOperator(): Boolean {
    if (prompt.textContent == null) return false

    return prompt.textContent!! == "+"
            || prompt.textContent!! == "-"
            || prompt.textContent!! == "*"
            || prompt.textContent!! == "/"
}

fun dispatch(operation: String): String {
    var amount = "Error"
    val x = operation.substringBefore(" ").toDouble()
    val y = operation.substringAfterLast(" ").toDoubleOrNull() ?: return x.toString()

    if (operation.contains("+")) amount = add(x, y).toString()
    else if (operation.contains("-")) amount = sub(x, y).toString()
    else if (operation.contains("*")) amount = multiply(x, y).toString()
    else if (operation.contains("/") && y != 0.0) amount = divide(x, y).toString()

    return amount
}

fun add(x: Double, y: Double): Double = x + y
fun sub(x: Double, y: Double): Double = x - y
fun multiply(x: Double, y: Double): Double = x * y

/**
 * @throws ArithmeticException but it should never happen
 */
fun divide(x: Double, y: Double): Double =
    if (y == 0.0) throw ArithmeticException("Division by 0 is not allowed") else x / y