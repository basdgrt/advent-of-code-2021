package solutions.day03

import solutions.PuzzleSolution

class BinaryDiagnostic : PuzzleSolution<Int>(fileName = "Day03.txt") {
    override fun firstStarSolution(puzzleInput: List<String>): Int {
        val gammaBinaryString = puzzleInput.determineGammaBinary()
        val epsilonBinaryString = gammaBinaryString.flipBits()
        return gammaBinaryString.toInt(radix = 2) * epsilonBinaryString.toInt(radix = 2)
    }

    override fun secondStarSolution(puzzleInput: List<String>): Int {
        val oxygenGeneratorBinary = puzzleInput.determineOxygenGeneratorBinary()
        val scrubberRatingBinary = puzzleInput.determineScrubberRatingBinary()
        return oxygenGeneratorBinary.toInt(radix = 2) * scrubberRatingBinary.toInt(radix = 2)
    }
}

private fun List<String>.determineGammaBinary(): String {
    return first().indices
        .map { columnNumber -> charactersPerColumnCount(columnNumber) }
        .joinToString(separator = "") { it.mostOccurringBitOrOne().toString() }
}

private fun List<String>.charactersPerColumnCount(columnNumber: Int): Map<Char, Int> {
    return groupingBy { it[columnNumber] }
        .eachCount()
}

private fun Map<Char, Int>.mostOccurringBitOrOne() = if (getValue('1') >= getValue('0')) '1' else '0'

private fun Char.flipBit() = if (this == '1') '0' else '1'

private fun String.flipBits() = toCharArray().joinToString(separator = "") { it.flipBit().toString() }

private fun List<String>.determineOxygenGeneratorBinary(): String {
    var oxygenGeneratorRatings = this

    for (column in first().indices) {
        val mostFrequentCharacter = oxygenGeneratorRatings.charactersPerColumnCount(column)
            .mostOccurringBitOrOne()
        oxygenGeneratorRatings = oxygenGeneratorRatings.filter { it[column] == mostFrequentCharacter }
        if (oxygenGeneratorRatings.size == 1) {
            break
        }
    }
    return oxygenGeneratorRatings.joinToString(separator = "")
}

private fun List<String>.determineScrubberRatingBinary(): String {
    var scrubberRatingBinary = this

    for (column in first().indices) {
        val leastFrequentCharacter = scrubberRatingBinary.charactersPerColumnCount(column)
            .mostOccurringBitOrOne()
            .flipBit()

        scrubberRatingBinary = scrubberRatingBinary.filter { it[column] == leastFrequentCharacter }
        if (scrubberRatingBinary.size == 1) {
            break
        }
    }
    return scrubberRatingBinary.joinToString(separator = "")
}
