package solutions.day08

class SevenSegmentDigit(input: List<String>) {
    private val candidates = input.toMutableList()

    fun length(l: Int) {
        candidates.removeIf { it.length != l }
    }

    fun matches(sevenSegmentDigit: SevenSegmentDigit, count: Int) {
        candidates.removeIf { candidate ->
            candidate.count { sevenSegmentDigit.letters.contains(it) } != count
        }
    }

    val letters get() = candidates.single().toCharArray().sorted().toString()
}

fun createDigits(signals: List<String>): Map<String, Int> {
    fun digit(lambda: SevenSegmentDigit.() -> Unit) = SevenSegmentDigit(signals).apply(lambda)

    val one = digit { length(2) }
    val four = digit { length(4) }
    val seven = digit { length(3) }
    val eight = digit { length(7) }

    val zero = digit {
        length(6)
        matches(four, 3)
        matches(seven, 3)
    }

    val two = digit {
        length(5)
        matches(one, 1)
        matches(four, 2)
    }

    val three = digit {
        length(5)
        matches(four, 3)
        matches(seven, 3)
    }

    val five = digit {
        length(5)
        matches(one, 1)
        matches(four, 3)
    }

    val six = digit {
        length(6)
        matches(one, 1)
    }

    val nine = digit {
        length(6)
        matches(four, 4)
        matches(seven, 3)
    }

    return mapOf(
        zero.letters to 0,
        one.letters to 1,
        two.letters to 2,
        three.letters to 3,
        four.letters to 4,
        five.letters to 5,
        six.letters to 6,
        seven.letters to 7,
        eight.letters to 8,
        nine.letters to 9,
    )
}