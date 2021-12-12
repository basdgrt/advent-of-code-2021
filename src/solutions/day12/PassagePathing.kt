package solutions.day12

import solutions.PuzzleSolution

const val START_CAVE_NAME = "start"
const val END_CAVE_NAME = "end"

class PassagePathing : PuzzleSolution<Int>(fileName = "Day12.txt") {
    override fun firstStarSolution(puzzleInput: List<String>): Int {
        return puzzleInput.mapToCaves()
            .fetchStartCave()
            .determinePossiblePaths(isAllowedToVisitSmallCaveTwice = false)
            .count()
    }

    override fun secondStarSolution(puzzleInput: List<String>): Int {
        return puzzleInput.mapToCaves()
            .fetchStartCave()
            .determinePossiblePaths(isAllowedToVisitSmallCaveTwice = true)
            .count()
    }
}

data class Cave(val name: String) {
    val connections = mutableMapOf<String, Cave>()
    private val isSmall = name.all { it.isLowerCase() }

    fun determinePossiblePaths(
        visitedCaveNames: Set<String> = setOf(START_CAVE_NAME),
        isAllowedToVisitSmallCaveTwice: Boolean
    ): List<Set<String>> {
        return connections.flatMap { (name, cave) ->
            when {
                name == END_CAVE_NAME -> setOf(visitedCaveNames.plus(name))
                name == START_CAVE_NAME -> emptyList()
                cave.isSmall ->
                    when {
                        visitedCaveNames.notContains(name) -> cave.determinePossiblePaths(
                            visitedCaveNames.plus(name),
                            isAllowedToVisitSmallCaveTwice
                        )
                        isAllowedToVisitSmallCaveTwice -> cave.determinePossiblePaths(visitedCaveNames, false)
                        else -> emptyList()
                    }
                else -> cave.determinePossiblePaths(visitedCaveNames.plus(name), isAllowedToVisitSmallCaveTwice)
            }
        }
    }
}

private fun Set<String>.notContains(value: String): Boolean {
    return !contains(value)
}

private fun List<String>.mapToCaves(): Map<String, Cave> {
    val caves = mutableMapOf<String, Cave>()

    forEach {
        val (thisCaveName, thatCaveName) = it.split("-")
        val caveOne = caves.getOrPut(thisCaveName) { Cave(thisCaveName) }
        val caveTwo = caves.getOrPut(thatCaveName) { Cave(thatCaveName) }

        caveOne.connections.putIfAbsent(thatCaveName, caveTwo)
        caveTwo.connections.putIfAbsent(thisCaveName, caveOne)
    }

    return caves
}

private fun Map<String, Cave>.fetchStartCave(): Cave {
    return this[START_CAVE_NAME] ?: throw IllegalStateException("Could not find start cave")
}