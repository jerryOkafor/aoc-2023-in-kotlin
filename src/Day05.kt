fun main() {
    fun List<String>.getMaps() = this.drop(2)
            .chunkedByEmptyLine()
            .map { section ->
                section.lines()
                        .drop(1) //drop section title
                        .associate {
                            it.split("\\s++".toRegex())
                                    .map { it.trim().toLong() }
                                    .let { (destinationRangeStart, sourceRangeStart, rangeLength) ->
                                        //98..100 -> 50..52 and 50..98 -> 52..100
                                        sourceRangeStart..(sourceRangeStart + rangeLength) to destinationRangeStart..(destinationRangeStart + rangeLength)
                                    }
                        }
            }

    fun part1(input: List<String>): Int {
        val seeds: List<Long> = input.splitOnEmpty().getSeeds()
        val maps: List<Map<LongRange, LongRange>> = input.getMaps()

        seeds.minOf { seed ->
            //using the given seed, we move from left to right and return the
            //value of the source (seed -> soil -> fertilizer -> water -> light -> temp -> humidity)
            //or the original value if not match until we end up with location value
            maps.fold(seed) { acc, map ->
                map.entries
                        .firstOrNull { acc in it.key }
                        ?.let { (source, destination) ->
                            destination.first + (acc - source.first)
                        } ?: acc
            }
        }.println("Min is")

        return input.size
    }

    fun getOutputRanges(inputSeedRange: LongRange, map: Map<LongRange, LongRange>): List<LongRange> {
        val mappedRanges = mutableListOf<LongRange>()
        val outputRange = map.entries.mapNotNull { (source, destination) ->
            val start = maxOf(source.first, inputSeedRange.first)
            val end = minOf(source.last, inputSeedRange.last)

            if (start <= end) {
                mappedRanges += start..end
                (destination.first - source.first).let {
                    (start + it)..(end + it)
                }
            } else null
        }

        val cutRange = listOf(inputSeedRange.first) + mappedRanges.sortedBy { it.first }
                .flatMap { listOf(it.first, it.last) } + listOf(inputSeedRange.last)

        val unMappedRanges = cutRange.chunked(2).mapNotNull { (first, second) ->
            if (second > first) {
                if (second == cutRange.last())
                    first..second
                else {
                    first..<second
                }
            } else {
                null
            }
        }
        return outputRange + unMappedRanges

    }

    fun part2(input: List<String>): Int {
        val seeds: List<LongRange> = input.splitOnEmpty()
                .getSeeds()
                .chunked(2)
                .map { it.first()..<it.first() + it.last() }
                .onEach(::println)
        val maps: List<Map<LongRange, LongRange>> = input.getMaps()

        seeds.flatMap { seedsRange ->
            maps.fold(listOf(seedsRange)) { acc, map ->
                acc.flatMap { runningSeedsRange ->
                    getOutputRanges(runningSeedsRange, map)
                }
            }
        }.minOf { it.first }.println("Min Value 2")

        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 33)

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
