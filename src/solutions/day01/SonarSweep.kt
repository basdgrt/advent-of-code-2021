package solutions.day01

import solutions.PuzzleSolution
import util.toInts

class SonarSweep : PuzzleSolution<Int>(fileName = "Day01.txt") {

    override fun firstStarSolution(puzzleInput: List<String>): Int {
        return puzzleInput.toInts()
            .zipWithNext { current, next -> Pair(current, next) }
            .count { it.first < it.second }
    }

    override fun secondStarSolution(puzzleInput: List<String>): Int {
        return puzzleInput.toInts()
            .windowed(size = 3, transform = { it.sum() })
            .zipWithNext { current, next -> Pair(current, next) }
            .count { it.first < it.second }
    }
}