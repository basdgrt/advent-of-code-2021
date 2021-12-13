package solutions.day13

import solutions.PuzzleSolution

class TransparentOrigami : PuzzleSolution<String>(fileName = "Day13.txt") {
    override fun firstStarSolution(puzzleInput: List<String>): String {
        val dots = puzzleInput.mapToDots()

        return puzzleInput.mapToFolds()
            .first()
            .execute(dots)
            .count()
            .toString()
    }

    override fun secondStarSolution(puzzleInput: List<String>): String {
        val dots = puzzleInput.mapToDots()

        val result = puzzleInput.mapToFolds()
            .fold(dots) { origamiPaper, foldInstruction ->
                foldInstruction.execute(origamiPaper)
            }

        return "\n${result.toPrintableString()}"
    }
}

data class Dot(val x: Int, val y: Int)

private fun List<String>.mapToDots(): Set<Dot> {
    return takeWhile { line -> line.isNotBlank() }
        .map { coordinates ->
            val (x, y) = coordinates.split(",")
            Dot(x.toInt(), y.toInt())
        }.toSet()
}

private fun List<String>.mapToFolds(): List<FoldInstruction> {
    return takeLastWhile { it.isNotBlank() }
        .map {
            val (axis, line) = it.removePrefix("fold along ").split("=")
            FoldInstruction.of(axis, line)
        }
}

fun Set<Dot>.toPrintableString(): String {
    val maxWidth = maxOf { it.x }
    val maxHeight = maxOf { it.y }

    val stringBuilder = StringBuilder()
    for (y in 0..maxHeight) {
        for (x in 0..maxWidth) {
            if (any { it.x == x && it.y == y }) stringBuilder.append('#') else stringBuilder.append(' ')
        }
        stringBuilder.append("\n")
    }
    return stringBuilder.toString()
}
