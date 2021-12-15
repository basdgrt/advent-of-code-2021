package solutions.day15

import solutions.PuzzleSolution

class Chiton : PuzzleSolution<Int>(fileName = "Day15.txt") {
    override fun firstStarSolution(puzzleInput: List<String>): Int {
        return puzzleInput
            .mapToRiskLevels(timesToExpand = 0)
            .calculatePathWithLowestTotalRisk()
    }

    override fun secondStarSolution(puzzleInput: List<String>): Int {
        return puzzleInput
            .mapToRiskLevels(timesToExpand = 5)
            .calculatePathWithLowestTotalRisk()
    }
}

private fun List<String>.mapToRiskLevels(timesToExpand: Int = 0): List<List<Int>> {
    return map { row ->
        row.map { riskLevel ->
            riskLevel.toString().toInt()
        }
    }.expandMap(timesToExpand)
}

private fun List<List<Int>>.expandMap(timesToExpand: Int): List<List<Int>> {
    if (timesToExpand <= 0) {
        return this
    }

    val width = first().size
    val height = size

    return (0 until height * timesToExpand)
        .map { y ->
            val yDelta = y / height
            val yMod = y % height

            (0 until width * timesToExpand)
                .map { x ->
                    val xDelta = x / width
                    val xMod = x % width

                    val riskLevel = this[yMod][xMod] + yDelta + xDelta
                    riskLevel.reset(9)
                }
        }
}

private fun Int.reset(max: Int): Int {
    return if (this > max) {
        this - max
    } else {
        this
    }
}