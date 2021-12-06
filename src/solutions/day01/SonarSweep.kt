package solutions.day01

import solutions.PuzzleSolution

class SonarSweep : PuzzleSolution<Int>(fileName = "Day01.txt") {

    override fun firstStarSolution(puzzleInput: List<String>): Int {
        return puzzleInput.map { it.trim().toInt() }
            .zipWithNext { current, next -> Pair(current, next) }
            .count { it.first < it.second }
    }

    override fun secondStarSolution(puzzleInput: List<String>): Int {
        return puzzleInput.map { it.trim().toInt() }
            .windowed(size = 3, transform = { it.sum() })
            .zipWithNext { current, next -> Pair(current, next) }
            .count { it.first < it.second }
    }
}