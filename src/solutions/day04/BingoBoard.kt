package solutions.day04

data class Field(val value: Int, val marked: Boolean = false)

class BingoBoard(private val fields: List<MutableList<Field>>) {
    companion object {
        fun fromPuzzleInput(puzzleInput: List<String>): List<BingoBoard> {
            val rawBoards = puzzleInput.drop(1)
                .chunked(6)
                .map { rows -> rows.filter { it.isNotBlank() } }

            val sanitizedBoards = rawBoards.map { rawBoard ->
                rawBoard.map { rawLine ->
                    rawLine.split("  ", " ")
                        .filter { it.isNotBlank() }
                        .map { it.toInt() }
                }
            }
            return sanitizedBoards.map { of2DimensionalList(it) }
        }

        private fun of2DimensionalList(intFields: List<List<Int>>): BingoBoard {
            val fields = intFields.map { row -> row.map { Field(it) }.toMutableList() }
            return BingoBoard(fields)
        }
    }

    fun update(bingoNumber: Int) {
        fields.forEach { row ->
            row.replaceAll { if (it.value == bingoNumber) it.copy(marked = true) else it }
        }
    }

    fun sumOfUnmarkedFieldValues(): Int {
        return fields.flatten()
            .filterNot { it.marked }
            .sumOf { it.value }
    }

    fun hasBingo() = hasBingoOnRow() || hasBingoOnColumn()

    private fun hasBingoOnRow() = fields.any { it.all { field -> field.marked } }

    private fun hasBingoOnColumn(): Boolean {
        fields.first().indices
            .forEachIndexed { columnIndex, _ ->
                var allFieldsInColumnMarked = true
                fields.indices.forEachIndexed { rowIndex, _ ->
                    if (!fields[rowIndex][columnIndex].marked) {
                        allFieldsInColumnMarked = false
                    }
                }
                if (allFieldsInColumnMarked) {
                    return true
                }
            }
        return false
    }
}