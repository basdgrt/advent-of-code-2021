package solutions.day05

import solutions.PuzzleSolution
import kotlin.math.abs

class HydrothermalVenture : PuzzleSolution<Int>(fileName = "Day05.txt") {
    override fun firstStarSolution(puzzleInput: List<String>): Int {
        val entries = puzzleInput.mapToEntries()
            .filter { entry -> entry.isStraightLine() }

        val diagram = Diagram.initializeDiagram(entries)
        entries.forEach { diagram.update(it) }
        return diagram.intersectionCount()
    }

    override fun secondStarSolution(puzzleInput: List<String>): Int {
        val entries = puzzleInput.mapToEntries()
        val diagram = Diagram.initializeDiagram(entries)
        entries.forEach { diagram.update(it) }
        return diagram.intersectionCount()
    }
}

data class Point(val x: Int, val y: Int)

data class Entry(val startPoint: Point, val endPoint: Point) {
    companion object {
        fun of(input: List<Int>): Entry {
            require(input.size == 4) { "input size has to be exactly 4" }
            val startPoint = Point(input[0], input[1])
            val endPoint = Point(input[2], input[3])
            return Entry(startPoint = startPoint, endPoint = endPoint)
        }
    }

    fun isStraightLine() = isHorizontalLine() || isVerticalLine()

    private fun isHorizontalLine() = startPoint.x == endPoint.x

    private fun isVerticalLine() = startPoint.y == endPoint.y

    private fun arrangeFromLowToHigh(): Entry {
        if (startPoint.x < endPoint.x) return this
        if (isHorizontalLine() && startPoint.y < endPoint.y) return this
        return Entry(endPoint, startPoint)
    }

    fun coversPoints(): List<Point> = with(arrangeFromLowToHigh()) {
        if (isHorizontalLine()) {
            return (startPoint.y.rangeTo(endPoint.y)).map { Point(startPoint.x, it) }
        }
        if (isVerticalLine()) {
            return (startPoint.x.rangeTo(endPoint.x)).map { Point(it, startPoint.y) }
        }

        val deltaX = endPoint.x - startPoint.x
        val deltaY = endPoint.y - startPoint.y
        val direction = if (deltaY == 0) 0 else deltaY / abs(deltaY)

        return (0..deltaX).map {
            Point(startPoint.x + it, startPoint.y + direction * it)
        }
    }
}

class Diagram(private val chart: Array<Array<Int>>) {
    companion object {
        fun initializeDiagram(entries: List<Entry>, defaultFieldValue: Int = 0): Diagram {
            val (height, width) = entries.maxHeightAndWidth()
            return Diagram(Array(height + 1) { Array(width + 1) { defaultFieldValue } })
        }
    }

    fun update(entry: Entry) {
        val points = entry.coversPoints()
        points.forEach {
            chart[it.y][it.x] = chart[it.y][it.x] + 1
        }
    }

    fun intersectionCount(): Int {
        return chart.flatten().filter { it > 1 }.count()
    }

    fun prettyPrint() {
        chart.forEach { row ->
            row.forEach { line -> print(line) }
            println()
        }
    }
}

private fun List<String>.mapToEntries(): List<Entry> {
    return map { line ->
        val lineWithoutWhitespace = line.filterNot { it.isWhitespace() }
        val values = lineWithoutWhitespace.replace("->", ",").split(",").map { it.toInt() }
        Entry.of(values)
    }
}

private fun List<Entry>.maxHeightAndWidth(): Pair<Int, Int> {
    val maxStartPointX = maxOf { it.startPoint.x }
    val maxStartPointY = maxOf { it.startPoint.y }
    val maxEndpointX = maxOf { it.endPoint.x }
    val maxEndpointY = maxOf { it.endPoint.y }

    val maxHeight = maxOf(maxStartPointY, maxEndpointY)
    val maxWidth = maxOf(maxStartPointX, maxEndpointX)

    return Pair(maxHeight, maxWidth)
}