package solutions.day13

sealed class FoldInstruction {
    abstract fun execute(dots: Set<Dot>): Set<Dot>

    companion object {
        fun of(axis: String, line: String): FoldInstruction {
            return when (axis) {
                "x" -> VerticalFoldInstruction(line.toInt())
                "y" -> HorizontalFoldInstruction(line.toInt())
                else -> throw IllegalStateException("Unknown axis $axis")
            }
        }
    }
}

data class HorizontalFoldInstruction(val line: Int) : FoldInstruction() {
    override fun execute(dots: Set<Dot>): Set<Dot> {
        return dots.map { dot ->
            if (dot.y < line) return@map dot else Dot(dot.x, dot.y - 2 * (dot.y - line))
        }.toSet()
    }
}

data class VerticalFoldInstruction(val line: Int) : FoldInstruction() {
    override fun execute(dots: Set<Dot>): Set<Dot> {
        return dots.map { dot ->
            if (dot.x < line) return@map dot else Dot(dot.x - 2 * (dot.x - line), dot.y)
        }.toSet()
    }
}