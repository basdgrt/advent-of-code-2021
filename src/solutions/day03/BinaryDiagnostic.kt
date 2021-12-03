package solutions.day03

import solutions.PuzzleSolution

class BinaryDiagnostic : PuzzleSolution<Int>(fileName = "Day03_test.txt") {
    override fun firstStarSolution(puzzleInput: List<String>): Int {
        val gammaBinaryString = determineGammaBinary(puzzleInput)
        val epsilonBinaryString = gammaBinaryString.flipBits()
        return gammaBinaryString.toInt(radix = 2) * epsilonBinaryString.toInt(radix = 2)
    }

    override fun secondStarSolution(puzzleInput: List<String>): Int {
        val oxygenGeneratorBinary = determineOxygenGeneratorBinary(puzzleInput)
        val scrubberRatingBinary = determineScrubberRatingBinary(puzzleInput)
        return oxygenGeneratorBinary.toInt(radix = 2) * scrubberRatingBinary.toInt(radix = 2)
    }

    private fun determineOxygenGeneratorBinary(puzzleInput: List<String>): String {
        val indices = puzzleInput.first().indices
        var oxygenGeneratorRatings = puzzleInput

        for (i in indices) {
            val mostFrequentCharacter = oxygenGeneratorRatings.charactersPerColumnCount(i).getMostOccurringCharacter()
            oxygenGeneratorRatings = oxygenGeneratorRatings.filter { it[i] == mostFrequentCharacter }
            if (oxygenGeneratorRatings.size == 1) {
                break
            }
        }

        return oxygenGeneratorRatings.joinToString(separator = "")
    }

    private fun determineScrubberRatingBinary(puzzleInput: List<String>): String {
        val indices = puzzleInput.first().indices
        var scrubberRatingBinary = puzzleInput

        for (i in indices) {
            val leastFrequentCharacter = scrubberRatingBinary.charactersPerColumnCount(i).getLeastOccurringCharacter()
            scrubberRatingBinary = scrubberRatingBinary.filter { it[i] == leastFrequentCharacter }
            if (scrubberRatingBinary.size == 1) {
                break
            }
        }

        return scrubberRatingBinary.joinToString(separator = "")
    }
}

private fun Map<Char, Int>.getMostOccurringCharacter(): Char {
    val numberOfOnes = getValue('1')
    val numberOfZeros = getValue('0')

    return if (numberOfOnes >= numberOfZeros) '1' else '0'
}

private fun Map<Char, Int>.getLeastOccurringCharacter(): Char {
    val numberOfOnes = getValue('1')
    val numberOfZeros = getValue('0')

    return if (numberOfOnes < numberOfZeros) '1' else '0'
}

private fun determineGammaBinary(puzzleInput: List<String>): String {
    val indices = puzzleInput.first().indices

    val gammaValue = indices.map { columnNumber -> puzzleInput.charactersPerColumnCount(columnNumber) }
        .joinToString(separator = "") { frequencyMap ->
            val mostFrequentChar = frequencyMap.maxByOrNull { it.value }?.key ?: throw IllegalStateException("Foo")
            mostFrequentChar.toString()
        }
    return gammaValue
}

private fun List<String>.charactersPerColumnCount(columnNumber: Int): Map<Char, Int> {
    return groupingBy { it[columnNumber] }.eachCount()
}

private fun String.flipBits(): String {
    return toCharArray().joinToString(separator = "") { if (it == '1') "0" else "1" }
}