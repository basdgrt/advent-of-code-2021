package solutions.day09

import solutions.PuzzleSolution

class SmokeBasin : PuzzleSolution<Int>(fileName = "Day09.txt") {
    override fun firstStarSolution(puzzleInput: List<String>): Int {
        val measurements = puzzleInput.mapToMeasurements()

        val lowPoints = mutableListOf<Measurement>()
        measurements.forEach { measurement ->
            val adjacentMeasurements = measurement.getAdjacentMeasurements(measurements)
            if (adjacentMeasurements.all { it.height > measurement.height }) {
                lowPoints.add(measurement)
            }
        }

        return lowPoints.sumOf { it.height + 1 }
    }

    override fun secondStarSolution(puzzleInput: List<String>): Int {
        val measurements = puzzleInput.mapToMeasurements()

        val lowPoints = mutableListOf<Measurement>()
        measurements.forEach { measurement ->
            val adjacentMeasurements = measurement.getAdjacentMeasurements(measurements)
            if (adjacentMeasurements.all { it.height > measurement.height }) {
                lowPoints.add(measurement)
            }
        }

        return lowPoints
            .asSequence()
            .map { findBasinByLowestPoint(it, measurements) }
            .distinct()
            .sortedByDescending { it.size }
            .take(3)
            .map { it.size }
            .reduce { a, b -> a * b }
    }
}

fun findBasinByLowestPoint(lowPoint: Measurement, originalMeasurements: List<Measurement>): Set<Measurement> {
    var currentBasin = setOf(lowPoint)
    var newBasin: Set<Measurement>
    while (true) {
        newBasin = expand(currentBasin, originalMeasurements)
        if (newBasin.size == currentBasin.size) return newBasin
        currentBasin = newBasin
    }
}

fun expand(currentBasin: Set<Measurement>, originalMeasurements: List<Measurement>): Set<Measurement> {
    val newBasin = currentBasin.toMutableSet()
    for (point in currentBasin) {
        newBasin.addAll(point.getAdjacentMeasurements(originalMeasurements).filter { it.height != 9 })
    }
    return newBasin
}

data class Measurement(val height: Int, val position: Position) {
    fun getAdjacentMeasurements(measurements: List<Measurement>): List<Measurement> {
        return measurements.filter { position.getAdjacentPositions().contains(it.position) }
    }
}

data class Position(val x: Int, val y: Int) {
    fun getAdjacentPositions(): List<Position> {
        return listOf(
            Position(x, y - 1),            // above
            Position(x + 1, y),            // right
            Position(x, y + 1),            // below
            Position(x - 1, y),            // left
        )
    }
}

private fun List<String>.mapToMeasurements(): List<Measurement> {
    val ints = map { it.toCharArray().map { char -> char.digitToInt() } }

    val measurements = mutableListOf<Measurement>()
    for (y in ints.indices) {
        for (x in ints[y].indices) {
            val position = Position(x, y)
            measurements.add(Measurement(position = position, height = ints[y][x]))
        }
    }
    return measurements.toList()
}