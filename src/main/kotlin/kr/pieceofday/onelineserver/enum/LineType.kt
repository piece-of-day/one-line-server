package kr.pieceofday.onelineserver.enum

enum class LineType(val title_korean: String, val color: String) {
    HAPPY("행복", "red"),
    SAD("슬픔", "blue"),
    STUDY("공부", "yellow"),
    END_YEAR("연말", "green"),
    FRIENDLY("친근", "purple");

    companion object {
        fun allValues(): List<LineTypeDTO> {
            return LineType.values()
                .map { LineTypeDTO(it.name, it.title_korean, it.color) }
        }
    }
}

class LineTypeDTO(
    val title: String,
    val title_korean: String,
    val color: String
)
