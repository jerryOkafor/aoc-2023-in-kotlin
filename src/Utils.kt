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
