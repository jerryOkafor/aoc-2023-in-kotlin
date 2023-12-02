fun main() {
    fun processInput(input: List<String>): List<Map<Int, List<Map<String, Int>>>> {
        return input.map { line ->
            val game = "^Game\\s(\\d+)".toRegex().find(line.substringBefore(":"))?.groupValues!!.last().toInt()

            val gameSets = line.substringAfter(":")
                    .split(";")
                    .fold(mutableListOf<Map<String, Int>>()) { acc, item ->
                        //find and add all the red, green and blue balls for eventy given set
                        val redsInSet = "(\\d+)\\sred".toRegex().find(item)?.groupValues?.lastOrNull()?.toIntOrNull()
                                ?: 0
                        val greensInSet = "(\\d+)\\sgreen".toRegex().find(item)?.groupValues?.lastOrNull()?.toIntOrNull()
                                ?: 0
                        val bluesInSet = "(\\d+)\\sblue".toRegex().find(item)?.groupValues?.lastOrNull()?.toIntOrNull()
                                ?: 0
                        acc.add(mapOf("red" to redsInSet, "green" to greensInSet, "blue" to bluesInSet))
                        acc
                    }

            mapOf(game to gameSets.toList())
        }
    }

    fun part1(input: List<String>): Int {
        val config = buildMap<String, Int> {
            put("red", 12)
            put("green", 13)
            put("blue", 14)
        }
        return processInput(input)
                .filterNot { games ->
                    games.any { sets ->
                        sets.value.any { set ->
                            set["red"]!! > config["red"]!! || set["green"]!! > config["green"]!! || set["blue"]!! > config["blue"]!!
                        }
                    }
                }.flatMap { it.keys }.sum()
    }

    fun part2(input: List<String>): Int {
        return processInput(input)
                .flatMap { games ->
                    games.values.map { sets ->
                        val maxRed = sets.maxBy { it["red"]!! }["red"]!!
                        val maxGreen = sets.maxBy { it["green"]!! }["green"]!!
                        val maxBlue = sets.maxBy { it["blue"]!! }["blue"]!!
                        mapOf("red" to maxRed, "green" to maxGreen, "blue" to maxBlue)
                    }
                }.sumOf {
                    it["red"]!! * it["green"]!! * it["blue"]!!
                }
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 8)

    val input = readInput("Day02")

    part1(testInput).println()
    part2(testInput).println()

    part1(input).println()
    part2(input).println()

}
