fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            val first = line.first(Char::isDigit)
            val last = line.last(Char::isDigit)
            "$first$last".toInt()
        }
    }

    fun part2(input: List<String>): Int {
        //build a comprehensive look up map with keys susch as one or 1 and values 1 i.e (1 -> 1) and (one -> 1)
        val digitMap = buildMap {
            listOf(
                    1 to "one",
                    2 to "two",
                    3 to "three",
                    4 to "four",
                    5 to "five",
                    6 to "six",
                    7 to "seven",
                    8 to "eight",
                    9 to "nine",
            ).forEach { (number, name) ->
                put(number.toString(), number)
                put(name, number)
            }
        }

        return input.sumOf { line ->

            //find the first occurence of any of the keys
            val minKey = line.findAnyOf(digitMap.keys)!!.second

            //find the last occrence of any of the keys
            val maxkey = line.findLastAnyOf(digitMap.keys)!!.second

            //use the look map and transform
            val min = digitMap.getValue(minKey)
            val max = digitMap.getValue(maxkey)

            "$min$max".toInt()
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    part1(testInput).println()
    check(part1(testInput) == 142)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}