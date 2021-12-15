package solutions.day15

import util.priorityQueueBy

data class Position(val x: Int, val y: Int) {
    fun getAdjacentPositions(maxX: Int, maxY: Int): Set<Position> {
        val adjacentPositions = mutableSetOf<Position>()
        if (x > 0) {
            adjacentPositions.add(Position(x - 1, y))
        }
        if (x < maxX) {
            adjacentPositions.add(Position(x + 1, y))
        }
        if (y > 0) {
            adjacentPositions.add(Position(x, y - 1))
        }
        if (y < maxY) {
            adjacentPositions.add(Position(x, y + 1))
        }
        return adjacentPositions.toSet()
    }
}

data class Vertex(val position: Position, val riskLevel: Int)

fun List<List<Int>>.calculatePathWithLowestTotalRisk(): Int {
    val maxX = first().indices.last
    val maxY = indices.last

    val visited = mutableSetOf<Position>()
    val unvisited = priorityQueueBy<Vertex> { it.riskLevel }

    unvisited.add(Vertex(Position(0, 0), 0))

    while (unvisited.isNotEmpty()) {
        val (position, riskLevel) = unvisited.poll()

        if (position.x == maxX && position.y == maxY) {
            return riskLevel
        }

        if (!visited.add(position)) {
            continue
        }

        position.getAdjacentPositions(maxX, maxY)
            .forEach { unvisited.add(Vertex(it, riskLevel + this[it.y][it.x])) }

    }

    throw IllegalStateException("Could not find path")
}
