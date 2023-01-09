package kr.pieceofday.onelineserver.enum

enum class LineType(val title_korean: String, val color: String) {
    HAPPY("행복", "#FFF6BD"),
    SAD("슬픔", "#8FBDD3"),
    STUDY("공부", "#90C8AC"),
    END_YEAR("연말", "#820000"),
    FRIENDLY("친근", "#FEBE8C"),
    LOVE("사랑", "#F4BFBF"),
    COMFORT("위로", "#48A0B6");

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
