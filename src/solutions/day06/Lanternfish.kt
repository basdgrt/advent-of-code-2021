package solutions.day06

import solutions.PuzzleSolution

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

private fun List<Generation>.simulate(amountOfDays: Int): List<Generation> {
    var generations = toList()

    repeat(amountOfDays) { generations = generations.simulateSingleDay() }

    return generations.toList()
}

private fun List<Generation>.simulateSingleDay(): List<Generation> {
    val mutableGenerations = toMutableList()
    mutableGenerations.forEach { it.decrementTimer() }

    mutableGenerations.filter { it.isReadyToRespawn() }
        .forEach { generationReadyToRespawn ->
            mutableGenerations.removeIf { it.isReadyToRespawn() || it.is6thGeneration() }

            val current6thGenFish = find { it.is6thGeneration() }?.numberOfFish ?: 0
            val new6thGenFish = generationReadyToRespawn.numberOfFish
            val updated6thGen = Generation(timer = 6, numberOfFish = current6thGenFish + new6thGenFish)

            mutableGenerations.addAll(listOf(updated6thGen, generationReadyToRespawn.copy(timer = 8)))
        }

    return mutableGenerations.toList()
}

data class Generation(private var timer: Int, val numberOfFish: Long) {
    fun decrementTimer() {
        timer--
    }

    fun isReadyToRespawn() = timer < 0

    fun is6thGeneration() = timer == 6
}

private fun List<String>.mapToGenerations(): List<Generation> {
    return first()
        .split(",")
        .map { it.trim().toInt() }
        .groupingBy { it }.eachCount()
        .map { Generation(timer = it.key, numberOfFish = it.value.toLong()) }
}
