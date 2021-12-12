package solutions.day10

sealed class Parsing
data class SyntaxError(val expected: Char?, val actual: Char) : Parsing()
data class Incomplete(val characters: List<Char>) : Parsing()