package solutions

import java.io.File

private const val RESOURCE_FOLDER = "src/main/resources"

abstract class PuzzleSolution<R>(private val fileName: String) {
    init {
        require(fileName.isNotBlank()) { "fileName cannot be blank" }
    }

    fun solveFirstPuzzle() {
        val puzzleInput = readPuzzleInput()
        val result = firstStarSolution(puzzleInput)
        println("First puzzle result: $result")
    }

    fun solveSecondPuzzle() {
        val puzzleInput = readPuzzleInput()
        val result = secondStarSolution(puzzleInput)
        println("Second puzzle result: $result")
    }

    protected abstract fun firstStarSolution(puzzleInput: List<String>): R
    protected abstract fun secondStarSolution(puzzleInput: List<String>): R

    private fun readPuzzleInput() = File(RESOURCE_FOLDER, fileName).readLines()
}
