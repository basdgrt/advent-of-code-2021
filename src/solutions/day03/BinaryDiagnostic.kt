package solutions.day03

import solutions.PuzzleSolution

class BinaryDiagnostic : PuzzleSolution<Int>(fileName = "Day03_test.txt") {
    override fun firstStarSolution(puzzleInput: List<String>): Int {
        val stringLength = puzzleInput.first().length
        val gammaRate = mutableListOf<Char>()

        for (i in 0 until stringLength) {
            var numberOfOnes = 0
            var numberOrZeros = 0

            puzzleInput.forEach {
                val current = it.toCharArray()[i]
                if (current == '1') numberOfOnes += 1 else numberOrZeros += 1
            }

            if (numberOfOnes > numberOrZeros) {
                gammaRate.add('1')
            } else {
                gammaRate.add('0')
            }
        }

        val epsilonRate = gammaRate.inverse()

        val gammaValue = Integer.parseInt(gammaRate.joinToString(separator = ""), 2)
        val epsilonValue = Integer.parseInt(epsilonRate.joinToString(separator = ""), 2)

        return gammaValue * epsilonValue
    }

    override fun secondStarSolution(puzzleInput: List<String>): Int {
        val stringLength = puzzleInput.first().length

        var oxygenGenaratorRatingInput = puzzleInput.oxygenGeneratorFilter(0)

        for (i in 1 until stringLength) {
            oxygenGenaratorRatingInput = oxygenGenaratorRatingInput.oxygenGeneratorFilter(i)

            if (oxygenGenaratorRatingInput.size == 1) {
                break
            }
        }

        var co2ScrubberRating = puzzleInput.co2ScrubberRating(0)

        for (i in 1 until stringLength) {
            co2ScrubberRating = co2ScrubberRating.co2ScrubberRating(i)

            if (co2ScrubberRating.size == 1) {
                break
            }
        }

        val oxygen = Integer.parseInt(oxygenGenaratorRatingInput.joinToString(separator = ""), 2)
        val co2 = Integer.parseInt(co2ScrubberRating.joinToString(separator = ""), 2)

        return oxygen * co2
    }
}

private fun List<String>.oxygenGeneratorFilter(index: Int): List<String> {
    var numberOfOnes = 0
    var numberOrZeros = 0

    forEach {
        val current = it.toCharArray()[index]
        if (current == '1') numberOfOnes += 1 else numberOrZeros += 1
    }

    return filter {
        if (numberOfOnes >= numberOrZeros) {
            it[index] == '1'
        } else {
            it[index] == '0'
        }
    }
}

private fun List<String>.co2ScrubberRating(index: Int): List<String> {
    var numberOfOnes = 0
    var numberOrZeros = 0

    forEach {
        val current = it.toCharArray()[index]
        if (current == '1') numberOfOnes += 1 else numberOrZeros += 1
    }

    return filter {
        if (numberOrZeros <= numberOfOnes) {
            it[index] == '0'
        } else {
            it[index] == '1'
        }
    }
}

private fun List<Char>.inverse(): List<Char> {
    return map { if (it == '1') '0' else '1' }
}