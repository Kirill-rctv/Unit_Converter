package converter
import java.util.*

fun main() {
    do {
        print("Enter what you want to convert (or exit): ")
        val scanner = Scanner(System.`in`)
        val input = scanner.nextLine().toLowerCase()
        val arrayOfInput: Array<String> = input.split(" ").toTypedArray()
        val num = arrayOfInput[0]
        if (arrayOfInput.size >= 3) if (num[0].isDigit() || num[1].isDigit() || num == "exit") {
            var stringFrom = arrayOfInput[1]
            var stringTo: String
            if (stringFrom == "degree" || stringFrom == "degrees") {
                stringFrom = arrayOfInput[2]
                stringTo = arrayOfInput[4]
            } else stringTo = arrayOfInput[3]
            if (stringTo == "degree" || stringTo == "degrees") stringTo = when (stringFrom) {
                arrayOfInput[1] -> arrayOfInput[4]
                arrayOfInput[2] -> arrayOfInput[5]
                else -> stringTo
            }
            val measureFrom = fromString(stringFrom)
            val measureTo = fromString(stringTo)
            println(Measure.convert(measureFrom, measureTo, num.toDouble()))
        } else println("Parse error")
    } while (num != "exit")
}
fun fromString(string: String): Measure {
    for (i in Measure.values()) if (i.toString().toLowerCase() == string.toLowerCase()) return i
    return Measure.NoThisMeasure
}
enum class Measure(val unit: Double, val single: String, val multiple: String, val type: String) {
    M(1.0, "meter", "meters", "Length"), Meter(1.0, "meter", "meters", "Length"), Meters(1.0, "meter", "meters", "Length"),
    Km(1000.0, "kilometer", "kilometers", "Length"), Kilometer(1000.0, "kilometer", "kilometers", "Length"), Kilometers(1000.0, "kilometer", "kilometers", "Length"),
    Cm(0.01, "centimeter", "centimeters", "Length"), Centimeter(0.01, "centimeter", "centimeters", "Length"), Centimeters(0.01, "centimeter", "centimeters", "Length"),
    Mm(0.001, "millimeter", "millimeters", "Length"), Millimeter(0.001, "millimeter", "millimeters", "Length"), Millimeters(0.001, "millimeter", "millimeters", "Length"),
    Mi(1609.35, "mile", "miles", "Length"), Mile(1609.35, "mile", "miles", "Length"), Miles(1609.35, "mile", "miles", "Length"),
    Yd(0.9144, "yard", "yards", "Length"), Yard(0.9144, "yard", "yards", "Length"), Yards(0.9144, "yard", "yards", "Length"),
    Ft(0.3048, "foot", "feet", "Length"), Foot(0.3048, "foot", "feet", "Length"), Feet(0.3048, "foot", "feet", "Length"),
    In(0.0254, "inch", "inches", "Length"), Inch(0.0254, "inch", "inches", "Length"), Inches(0.0254, "inch", "inches", "Length"),
    G(1.0, "gram", "grams", "Weight"), Gram(1.0, "gram", "grams", "Weight"), Grams(1.0, "gram", "grams", "Weight"),
    Kg(1000.0, "kilogram", "kilograms", "Weight"), Kilogram(1000.0, "kilogram", "kilograms", "Weight"), Kilograms(1000.0, "kilogram", "kilograms", "Weight"),
    Mg(0.001, "milligram", "milligrams", "Weight"), Milligram(0.001, "milligram", "milligrams", "Weight"), Milligrams(0.001, "milligram", "milligrams", "Weight"),
    Lb(453.592, "pound", "pounds", "Weight"), Pound(453.592, "pound", "pounds", "Weight"), Pounds(453.592, "pound", "pounds", "Weight"),
    Oz(28.3495, "ounce", "ounces", "Weight"), Ounce(28.3495, "ounce", "ounces", "Weight"), Ounces(28.3495, "ounce", "ounces", "Weight"),
    Celsius(0.0, "degree Celsius", "degrees Celsius", "Temperature"), Dc(0.0, "degree Celsius", "degrees Celsius", "Temperature"), C(0.0, "degree Celsius", "degrees Celsius", "Temperature"),
    Fahrenheit(0.0, "degree Fahrenheit", "degrees Fahrenheit", "Temperature"), Df(0.0, "degree Fahrenheit", "degrees Fahrenheit", "Temperature"), F(0.0, "degree Fahrenheit", "degrees Fahrenheit", "Temperature"),
    Kelvin(0.0, "kelvin", "kelvins", "Temperature"), Kelvins(0.0, "kelvin", "kelvins", "Temperature"), K(0.0, "kelvin", "kelvins", "Temperature"),
    NoThisMeasure(0.0, "???", "???", "???");

    companion object{
        fun convert(from: Measure, to: Measure, num: Double): String {
            if (to == NoThisMeasure) return "Conversion from ${from.multiple} to ${to.type} is impossible"
            val numAfterConvert: Double
            when (from.type) {
                to.type -> when (from.type) {
                    "Length", "Weight" -> if (num > 0) numAfterConvert = num * from.unit / to.unit else return "${from.type} shouldn't be negative"
                    "Temperature" -> numAfterConvert = when {
                        (from == C || from == Dc || from == Celsius) && (to == C || to == Dc || to == Celsius) -> num
                        (from == C || from == Dc || from == Celsius) && (to == F || to == Df || to == Fahrenheit) -> num * 9.0 / 5.0 + 32.0
                        (from == C || from == Dc || from == Celsius) && (to == K || to == Kelvin || to == Kelvins) -> num + 273.15
                        (from == F || from == Df || from == Fahrenheit) && (to == F || to == Df || to == Fahrenheit) -> num
                        (from == F || from == Df || from == Fahrenheit) && (to == C || to == Dc || to == Celsius) -> (num - 32.0) * 5.0 / 9.0
                        (from == F || from == Df || from == Fahrenheit) && (to == K || to == Kelvin || to == Kelvins) -> (num + 459.67) * 5.0 / 9.0
                        (from == K || from == Kelvin || from == Kelvins) && (to == K || to == Kelvin || to == Kelvins) -> num
                        (from == K || from == Kelvin || from == Kelvins) && (to == C || to == Dc || to == Celsius) -> num - 273.15
                        (from == K || from == Kelvin || from == Kelvins) && (to == F || to == Df || to == Fahrenheit) -> num * 9.0 / 5.0 - 459.67
                        else -> num + 9_999_999.0
                    }
                    else -> return "Conversion from ${from.type} to ${to.multiple} is impossible"
                }
                else -> return "Conversion from ${from.multiple} to ${to.multiple} is impossible"
            }
            val measureFrom = if (num >= 1.0 && num < 2.0) from.single else from.multiple
            val measureTo = if (numAfterConvert >= 1.0 && numAfterConvert < 2.0) to.single else to.multiple
            return "$num $measureFrom is $numAfterConvert $measureTo"
        }
    }
}

unit.va