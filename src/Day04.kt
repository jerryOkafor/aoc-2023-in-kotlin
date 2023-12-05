typealias CardSet = Set<Int>

data class Card(val row: Int, val winnings: Set<Int>, val otherNumbers: Set<Int>)

fun main() {
    fun List<String>.buildGame() = this.map { it.substringAfter(":").trim() }
        .map { it.split("|").map { it.trim() } }
        .map { it.map { nums -> nums.split("\\s+".toRegex()).map { it.toInt() }.toSet() } }

    fun part1(input: List<String>): Int {
        return input.buildGame()
            .map { cardRow ->
                val (winingNumbers, otherNumbers) = cardRow
                otherNumbers.intersect(winingNumbers).sorted()
            }.filter { it.isNotEmpty() }
            .map {
                it.fold(0) { acc, i ->
                    if (acc == 0) {
                        acc + 1
                    } else {
                        acc * 2
                    }
                }
            }.sum()

    }

    fun part2(input: List<String>): Int {
        return input.buildGame()
            .mapIndexed { index, row ->
                val (winnings, otherNumbers) = row
                Card(row = index, winnings = winnings, otherNumbers = otherNumbers)
            }.let { games ->
                //Keep a running copy of all the scratch card won from top to bottom starting wit 1
                val copies = games.associate { card -> card.row to 1 }.toMutableMap()

                games.forEach {
                    val winningCount = it.winnings.intersect(it.otherNumbers).size
                    for (card in it.row + 1..it.row + winningCount) {
                        copies[card] = copies[card]!! + copies[it.row]!!
                    }

                }
                copies.println()

                copies.values.sum()
            }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 13)

    val input = readInput("Day04")
    part1(input).println("Won: ")

    part2(testInput).println()
    part2(input).println()

}
