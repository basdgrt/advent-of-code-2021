package solutions.day06

import solutions.PuzzleSolution
import util.toInts

class Lanternfish : PuzzleSolution<Long>(fileName = "Day06.txt") {
    override fun firstStarSolution(puzzleInput: List<String>): Long {
        return puzzleInput.mapToGenerations()
            .simulate(amountOfDays = 80)
            .sumOf { it.numberOfFish }
    }

    override fun secondStarSolution(puzzleInput: List<String>): Long {
        return puzzleInput.mapToGenerations()
            .simulate(amountOfDays = 256)
            .sumOf { it.numberOfFish }
    }
}

data class Generation(private var timer: Int, val numberOfFish: Long) {
    fun decrementTimer() {
        timer -= 1
    }

    fun isReadyToRespawn() = timer < 0

    fun hasTimerValue(expectedValue: Int) = timer == expectedValue
}

private fun List<String>.mapToGenerations(): List<Generation> {
    return first()
        .split(",")
        .toInts()
        .groupingBy { it }.eachCount()
        .map { Generation(timer = it.key, numberOfFish = it.value.toLong()) }
}

private fun List<Generation>.simulate(amountOfDays: Int): List<Generation> {
    val generations = toMutableList()

    repeat(amountOfDays) {
        generations.forEach { it.decrementTimer() }
        if (generations.any { it.isReadyToRespawn() }) {
            val generationReadyToRespawn = generations.find { it.isReadyToRespawn() }
                ?: throw IllegalStateException("Could not find generation to respawn")

            generations.add(generationReadyToRespawn.copy(timer = 8))
            val numberOfGeneration6Fish = generations.find { it.hasTimerValue(6) }?.numberOfFish ?: 0
            generations.removeIf { it.hasTimerValue(6) }
            generations.add(Generation(timer = 6, numberOfFish = generationReadyToRespawn.numberOfFish + numberOfGeneration6Fish))
        }
        generations.removeIf { it.isReadyToRespawn() }
    }
    return generations.toList()
}
