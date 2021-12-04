package solutions.day04

import solutions.PuzzleSolution

class GiantSquid : PuzzleSolution<Int>("Day04.txt") {
    override fun firstStarSolution(puzzleInput: List<String>): Int {
        val winningBoards = playBingo(puzzleInput)
        val winner = winningBoards.first()
        return winner.bingoBoard.sumOfUnmarkedFieldValues() * winner.winningNumber
    }

    override fun secondStarSolution(puzzleInput: List<String>): Int {
        val winningBoards = playBingo(puzzleInput)
        val winner = winningBoards.last()
        return winner.bingoBoard.sumOfUnmarkedFieldValues() * winner.winningNumber
    }
}

data class Field(val value: Int, val marked: Boolean = false)

data class Winner(val bingoBoard: BingoBoard, val winningNumber: Int)

private fun playBingo(puzzleInput: List<String>): MutableList<Winner> {
    val bingoNumbers = puzzleInput.mapToBingoNumbers()
    val bingoBoards = BingoBoard.fromPuzzleInput(puzzleInput)
    return determineWinners(bingoNumbers, bingoBoards)
}

private fun determineWinners(bingoNumbers: List<Int>, allBingoBoards: List<BingoBoard>): MutableList<Winner> {
    var bingoBoards = allBingoBoards
    val winningBoards = mutableListOf<Winner>()
    bingoNumbers.forEach { bingoNumber ->
        bingoBoards.forEach { bingoBoard ->
            bingoBoard.update(bingoNumber)
            if (bingoBoard.hasBingo()) {
                winningBoards.add(Winner(bingoBoard, bingoNumber))
                bingoBoards = bingoBoards - bingoBoard
            }
        }
    }
    return winningBoards
}

private fun List<String>.mapToBingoNumbers(): List<Int> {
    return first()
        .split(",")
        .map { it.trim().toInt() }
}