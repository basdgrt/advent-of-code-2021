package solutions.day14

import solutions.PuzzleSolution

class ExtendedPolymerization : PuzzleSolution<Long>(fileName = "Day14_test.txt") {
    override fun firstStarSolution(puzzleInput: List<String>): Long {
        val polymerTemplate = puzzleInput.first()
        val rules = puzzleInput.drop(2)
            .mapToRules()

        return polymerTemplate.transformPolymer(rules, 10)
            .countChars(polymerTemplate.first())
            .calculateScore()
    }

    override fun secondStarSolution(puzzleInput: List<String>): Long {
        val polymerTemplate = puzzleInput.first()
        val rules = puzzleInput.drop(2)
            .mapToRules()

        return polymerTemplate.transformPolymer(rules, 40)
            .countChars(polymerTemplate.first())
            .calculateScore()
    }
}

private fun List<String>.mapToRules(): Map<String, List<String>> {
    return associate { line ->
        line.split(" -> ")
            .let { rule ->
                rule.first() to listOf(
                    "${rule[0][0]}${rule[1]}",
                    "${rule[1]}${rule[0][1]}"
                )
            }
    }
}

private fun String.transformPolymer(rules: Map<String, List<String>>, generations: Int): Map<String, Long> {
    var polymerSnippetCounts = rules.keys
        .associateWith { 0L }
        .toMutableMap()

    zipWithNext().forEach { (a, b) ->
        polymerSnippetCounts["$a$b"] = polymerSnippetCounts.getOrDefault("$a$b", 0L) + 1
    }

    repeat(generations) {
        val updatedCounts = mutableMapOf<String, Long>()

        polymerSnippetCounts.forEach { (key, count) ->
            val newPairs = rules[key]!!
            updatedCounts[newPairs[0]] = updatedCounts.getOrDefault(newPairs[0], 0L) + count
            updatedCounts[newPairs[1]] = updatedCounts.getOrDefault(newPairs[1], 0L) + count
        }
        polymerSnippetCounts = updatedCounts
    }
    return polymerSnippetCounts
}

private fun Map<String, Long>.countChars(extraChar: Char): Map<Char, Long> {
    return entries.fold(
        mapOf(extraChar to 1L)
    ) {
        counts, (key, count) ->
        counts.plus(key[1] to counts.getOrDefault(key[1], 0L) + count)
    }
}

private fun Map<Char, Long>.calculateScore(): Long {
    val max = maxByOrNull { it.value }?.value ?: throw Error("No value found")
    val min = minByOrNull { it.value }?.value ?: throw Error("No value found")
    return max - min
}
