package solutions.day01

import solutions.PuzzleSolution
import util.toInts

class SonarSweep : PuzzleSolution<Int>(fileName = "Day01.txt") {

    override fun firstStarSolution(puzzleInput: List<String>): Int {
        return puzzleInput.toInts()
            .windowed(size = 2, step = 1)
            .count { it[0] < it[1] }
    }

    override fun secondStarSolution(puzzleInput: List<String>): Int {
        return puzzleInput.toInts()
            .windowed(size = 3, step = 1)
            .map { it.sum() }
            .windowed(size = 2, step = 1)
            .count { it[0] < it[1] }
    }
}