package solutions.day11

import solutions.PuzzleSolution

class DumboOctopus : PuzzleSolution<Int>(fileName = "Day11.txt") {
    override fun firstStarSolution(puzzleInput: List<String>): Int {
        val octopuses = puzzleInput.mapToOctopuses()

        var flashCounter = 0
        repeat(100) {
            octopuses.forEach { it.incrementEnergyLevel() }

            while (octopuses.any { octopus -> octopus.isReadyToFlash() }) {
                val octopusesReadyToFlash = octopuses.filter { octopus -> octopus.isReadyToFlash() }

                octopusesReadyToFlash.forEach {
                    it.flash()
                    flashCounter++

                    it.getAdjacentOctopuses(octopuses).filter { it.energyLevel != 0 }
                        .forEach { it.incrementEnergyLevel() }
                }
            }
        }

        return flashCounter
    }

    override fun secondStarSolution(puzzleInput: List<String>): Int {
        val octopuses = puzzleInput.mapToOctopuses()

        var stepCounter = 0
        while(true) {
            if (octopuses.all { it.energyLevel == 0 }) {
                break
            }

            stepCounter++

            octopuses.forEach { it.incrementEnergyLevel() }

            while (octopuses.any { octopus -> octopus.isReadyToFlash() }) {
                val octopusesReadyToFlash = octopuses.filter { octopus -> octopus.isReadyToFlash() }

                octopusesReadyToFlash.forEach {
                    it.flash()

                    it.getAdjacentOctopuses(octopuses).filter { it.energyLevel != 0 }
                        .forEach { it.incrementEnergyLevel() }
                }
            }
        }

        return stepCounter
    }
}

data class Octopus(val position: Position, var energyLevel: Int) {
    fun getAdjacentOctopuses(octopuses: List<Octopus>): List<Octopus> {
        return octopuses.filter { position.getAdjacentPositions().contains(it.position) }
    }

    fun incrementEnergyLevel() {
        energyLevel += 1
    }

    fun flash() {
        energyLevel = 0
    }

    fun isReadyToFlash(): Boolean {
        return energyLevel >= 10
    }
}

data class Position(val x: Int, val y: Int) {
    fun getAdjacentPositions(): List<Position> {
        return listOf(
            Position(x, y - 1),            // above
            Position(x + 1, y - 1),     // upper right
            Position(x + 1, y),            // right
            Position(x + 1, y + 1),     // bottom right
            Position(x, y + 1),            // below
            Position(x - 1, y + 1),     // bottom left
            Position(x - 1, y),            // left
            Position(x - 1, y - 1),     // upper left
        )
    }
}

private fun List<String>.mapToOctopuses(): List<Octopus> {
    val map = map { it.toCharArray().map { char -> char.digitToInt() } }

    val octopuses = mutableListOf<Octopus>()
    for (y in map.indices) {
        for (x in map.indices) {
            val position = Position(x, y)
            octopuses.add(Octopus(position = position, energyLevel = map[y][x]))
        }
    }
    return octopuses.toList()
}

