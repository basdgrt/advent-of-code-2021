package solutions.day10

import solutions.PuzzleSolution

private val MAPPINGS = mapOf(
    '(' to ')',
    '[' to ']',
    '{' to '}',
    '<' to '>'
)

private val ILLEGAL_CHAR_SCORES = mapOf(
    ')' to 3,
    ']' to 57,
    '}' to 1197,
    '>' to 25137
)

private val INCOMPLETE_CHAR_SCORES = mapOf(
    ')' to 1,
    ']' to 2,
    '}' to 3,
    '>' to 4
)

class SyntaxScoring : PuzzleSolution<Long>(fileName = "Day10.txt") {
    override fun firstStarSolution(puzzleInput: List<String>): Long {
        return puzzleInput.map { parse(it) }
            .filterSyntaxErrors()
            .sumOf { syntaxError -> ILLEGAL_CHAR_SCORES.getValue(syntaxError.actual) }
            .toLong()
    }

    override fun secondStarSolution(puzzleInput: List<String>): Long {
        val total = puzzleInput.map { parse(it) }
            .filterIncomplete()
            .map { incomplete ->
                incomplete.characters.fold(0L) { missingCharCount, missingChar ->
                    missingCharCount * 5 + INCOMPLETE_CHAR_SCORES.getValue(missingChar)
                }
            }.sorted()

        return total[total.size / 2]
    }

    private fun List<Parsing>.filterSyntaxErrors(): List<SyntaxError> {
        return filterIsInstance<SyntaxError>()
    }

    private fun List<Parsing>.filterIncomplete(): List<Incomplete> {
        return filterIsInstance<Incomplete>()
    }

    private fun parse(line: String): Parsing {
        val deque = ArrayDeque<Char>()

        line.forEach { character ->
            if (character.isOpening()) {
                deque.addLast(MAPPINGS.getValue(character))
            }
            else {
                val expectedClosingCharacter = deque.removeLastOrNull()
                if (expectedClosingCharacter != character) {
                    return SyntaxError(expected = expectedClosingCharacter, actual = character)
                }
            }
        }
        return Incomplete(deque.reversed())
    }

    private fun Char.isOpening(): Boolean {
        return this in MAPPINGS.keys
    }
}