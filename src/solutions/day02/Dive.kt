package solutions.day02

import solutions.PuzzleSolution

private val MATCH_ON_WHITESPACE = "\\s".toRegex()

class Dive : PuzzleSolution<Int>(fileName = "Day02.txt") {

    override fun firstStarSolution(puzzleInput: List<String>): Int {
        return puzzleInput.mapToCommands()
            .determineEndPosition()
            .multiplyHorizontalWithDepth()
    }

    override fun secondStarSolution(puzzleInput: List<String>): Int {
        return puzzleInput.mapToCommands()
            .determineEndPositionWithAim()
            .multiplyHorizontalWithDepth()
    }
}

private fun List<String>.mapToCommands(): List<Command> {
    return map { it.split(regex = MATCH_ON_WHITESPACE).createCommand() }
}

private fun List<String>.createCommand(): Command {
    check(size == 2) { "Expected exactly 2 elements but found $size elements" }
    return Command(Action.valueOf(first().uppercase()), last().toInt())
}

private fun List<Command>.determineEndPosition(): Position {
    val position = Position(horizontal = 0, depth = 0)
    forEach {
        when (it.action) {
            Action.UP -> position.moveUp(it.value)
            Action.DOWN -> position.moveDown(it.value)
            Action.FORWARD -> position.moveForward(it.value)
        }
    }
    return position
}

private fun List<Command>.determineEndPositionWithAim(): Position {
    val position = Position(horizontal = 0, depth = 0)
    var aim = 0

    forEach {
        when (it.action) {
            Action.UP -> aim -= it.value
            Action.DOWN -> aim += it.value
            Action.FORWARD -> {
                position.moveForward(it.value)
                position.moveDown(aim * it.value)
            }
        }
    }
    return position
}

enum class Action {
    UP,
    DOWN,
    FORWARD
}

data class Command(val action: Action, val value: Int)

data class Position(private var horizontal: Int, private var depth: Int) {
    fun moveUp(value: Int) {
        depth -= value
    }

    fun moveDown(value: Int) {
        depth += value
    }

    fun moveForward(value: Int) {
        horizontal += value
    }

    fun multiplyHorizontalWithDepth() = horizontal * depth
}