package solutions.day07

import solutions.PuzzleSolution
import kotlin.math.abs

class TreacheryOfWhales : PuzzleSolution<Int>(fileName = "Day07.txt") {
    override fun firstStarSolution(puzzleInput: List<String>): Int {
        val calculateFuelCostFn: (Int, Int) -> Int = { position, target ->
            abs(target - position)
        }

        return puzzleInput.toHorizontalPositions()
            .findLowestFuelCost(calculateFuelCostFn)
    }

    override fun secondStarSolution(puzzleInput: List<String>): Int {
        val calculateFuelCostFn: (Int, Int) -> Int = { position, target ->
            val distance = abs(target - position)
            (distance * (distance + 1)) / 2
        }

        return puzzleInput.toHorizontalPositions()
            .findLowestFuelCost(calculateFuelCostFn)
    }
}

private fun List<Int>.findLowestFuelCost(calculateFuelCostFn: (Int, Int) -> Int): Int {
    val minimal = minOrNull() ?: throw IllegalStateException("No min value found")
    val maximal = maxOrNull() ?: throw IllegalStateException("No max value found")

    val positionRange = minimal..maximal

    return positionRange.minOf { target ->
        this.sumOf { position -> calculateFuelCostFn(position, target) }
    }
}

private fun List<String>.toHorizontalPositions(): List<Int> {
    return first().split(",")
        .map { it.trim().toInt() }
}
