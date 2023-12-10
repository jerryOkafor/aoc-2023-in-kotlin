import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readLines

/** Reads lines from the given input txt file. */
fun readInput(name: String) = Path("/src/input/$name.txt").readLines()

fun readInputForNotebook(name: String) = Path("../src/input/$name.txt").readLines()

/** Converts string to md5 hash. */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/** The cleaner shorthand for printing output. */
fun Any?.println(desc: String = "") = kotlin.io.println("${if (desc.isNotBlank()) "$desc:\n" else ""}$this")


fun List<String>.splitOnEmpty() = filter { it.isNotBlank() }

fun List<String>.chunkedByEmptyLine() = this.joinToString("\n")
    .split("\n\n")

fun List<String>.getSeeds() = this.first()
    .substringAfter(":")
    .trim()
    .split("\\s++".toRegex())
    .map { it.trim().toLong() }


//lcm(a, b) = |a*b| / gcd(a,b)

fun greatestCommonDivisor(a: Int, b: Int): Int {
    var num1 = a
    var num2 = b
    while (num2 != 0) {
        val temp = num2
        num2 = num1 % num2
        num1 = temp
    }
    return num1
}

fun leastCommonMultiple(a: Int, b: Int): Int {
    val larger = if (a > b) a else b
    val maxLcm = a * b
    var lcm = larger
    while (lcm <= maxLcm) {
        if (lcm % a == 0 && lcm % b == 0) {
            return lcm
        }
        lcm += larger
    }
    return maxLcm
}

fun greatestCommonDivisor(a: Double, b: Double): Double {
    var num1 = a
    var num2 = b
    while (num2 != 0.0) {
        val temp = num2
        num2 = num1 % num2
        num1 = temp
    }
    return num1
}

fun leastCommonMultiple(a: Double, b: Double): Double {
    val larger = if (a > b) a else b
    val maxLcm = a * b
    var lcm = larger
    while (lcm <= maxLcm) {
        if (lcm % a == 0.0 && lcm % b == 0.0) {
            return lcm
        }
        lcm += larger
    }
    return maxLcm
}

fun greatestCommonDivisor(a: Long, b: Long): Long {
    var num1 = a
    var num2 = b
    while (num2 != 0L) {
        val temp = num2
        num2 = num1 % num2
        num1 = temp
    }
    return num1
}

fun leastCommonMultiple(a: Long, b: Long): Long {
    val larger = if (a > b) a else b
    val maxLcm = a * b
    var lcm = larger
    while (lcm <= maxLcm) {
        if (lcm % a == 0L && lcm % b == 0L) {
            return lcm
        }
        lcm += larger
    }
    return maxLcm
}
