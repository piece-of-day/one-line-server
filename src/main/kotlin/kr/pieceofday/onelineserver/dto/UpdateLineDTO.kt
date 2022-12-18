package kr.pieceofday.onelineserver.dto

import kr.pieceofday.onelineserver.enum.LineType

data class UpdateLineDTO(
    val title: LineType,
    val content: String
)
