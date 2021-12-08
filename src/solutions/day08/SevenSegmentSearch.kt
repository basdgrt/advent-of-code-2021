package solutions.day08

import solutions.PuzzleSolution

class SevenSegmentSearch : PuzzleSolution<Int>(fileName = "Day08.txt") {
    override fun firstStarSolution(puzzleInput: List<String>): Int {
        return puzzleInput.map { it.split("|").last() }
            .map { outputValues -> outputValues.split(" ").map { it.length } }
            .flatten()
            .count { it in listOf(2, 3, 4, 7) }
    }

    override fun secondStarSolution(puzzleInput: List<String>): Int {
        return puzzleInput.map { it.split(" | ") }
            .sumOf { (inputSignals, output) ->
                val digitSignals = createDigits(inputSignals.split(" "))
                val outputSignals = output.split(" ").map { it.toCharArray().sorted().toString() }

                digitSignals[outputSignals[0]]!! * 1000 +
                        digitSignals[outputSignals[1]]!! * 100 +
                        digitSignals[outputSignals[2]]!! * 10 +
                        digitSignals[outputSignals[3]]!!
            }
    }
}