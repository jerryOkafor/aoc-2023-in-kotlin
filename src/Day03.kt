//Important points
//Symbols must be adjacent -> next to, even diagonally.
//Diagonally can be left to right ot right to left. When you mix adjacent(next to) + diagonal, it means we are interested in left to right diagonal

//Given a number 462, then
//        * * * * *
//        * 4 6 1 *
//        * * * * *
//All the points * are adjacent to the number 461


fun IntRange.expandBy(value: Int) = first - value..last + value

fun Int.toRange() = this..this

sealed interface Element

data class PartNumber(val value: Int, val row: Int, val xRange: IntRange) : Element {
    val adjacentColSpan = xRange.expandBy(1)
    val adjacentRowSpan = row.toRange().expandBy(1)
}

fun buildNumber(value: String, row: Int, xRange: IntRange): PartNumber =
    PartNumber(value = value.toInt(), row = row, xRange = xRange)

fun buildNumber(value: String, row: Int, xStart: Int, xEnd: Int): PartNumber =
    PartNumber(value = value.toInt(), row = row, xRange = xStart..xEnd)

data class Symbol(val value: Char, val row: Int, val xRange: IntRange) : Element {
    val col: Int = xRange.first //or xRange.last
}

typealias SchematicRow = MutableList<Element>

fun main() {

    fun buildSchematic(input: List<String>): List<SchematicRow> =
        input.foldIndexed(mutableListOf<SchematicRow>()) { index, acc, line ->
            val numbers = "\\d++".toRegex().findAll(line)
            val symbols = "[^.\\s0-9]".toRegex().findAll(line)

            val iterator = numbers.iterator()
            val symbolsItertor = symbols.iterator()

            val schematicRows = mutableListOf<Element>()
            while (iterator.hasNext()) {
                val i = iterator.next()
                schematicRows.add(buildNumber(value = i.value, row = index, xRange = i.range))
            }

            while (symbolsItertor.hasNext()) {
                val s = symbolsItertor.next()
                schematicRows.add(Symbol(value = s.value.toCharArray().first(), row = index, xRange = s.range))
            }

            acc.add(schematicRows)
            acc
        }

    fun List<SchematicRow>.findPartNumbers(): Set<PartNumber> {
        val result = mutableSetOf<PartNumber>()

        //Use the windowed api here
        this.windowed(2).map { rowPair ->
            val symbols = rowPair.flatten().filterIsInstance<Symbol>()
            val partNumbers = rowPair.flatten().filterIsInstance<PartNumber>()

            partNumbers.filter { p ->
                symbols.any { s ->
                    s.xRange.last() in p.adjacentColSpan
                }
            }.forEach { result.add(it) }
        }
        return result
    }

    fun List<Element>.findGearParts(): List<Pair<PartNumber, PartNumber>> {
        val partNumbers = this.filterIsInstance<PartNumber>()
        val symbolsThatCouldBeGears = this.filterIsInstance<Symbol>().filter { it.value == '*' }

        return symbolsThatCouldBeGears.map { s ->
            partNumbers.filter { p ->
                //Take all neighbors
                s.row in p.adjacentRowSpan && s.xRange.first in p.adjacentColSpan
            }
        }.filter {
            //take neighbors that adjacent to the gear symbol
            it.size == 2
        }.map { it[0] to it[1] }
    }

    fun part1(input: List<String>): Int {
        //Go through each row, record numbers, index of the last digit
        val engineSchematic = buildSchematic(input)
        val partNumbers = engineSchematic.findPartNumbers()

        return partNumbers.sumOf { it.value }
    }

    fun part2(input: List<String>): Int {
        val engineSchematic = buildSchematic(input)
        return engineSchematic
            .flatten()
            .findGearParts()
            .sumOf { it.first.value * it.second.value }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 4361)
    part1(testInput).println("Sum of all part number 1")
    part2(testInput).println("Sum of all Gears 1")

    "________________".println()

    val input = readInput("Day03")
    part1(input).println("Sum of all part number 2")
    part2(input).println("Sum of all Gears 2")
}
