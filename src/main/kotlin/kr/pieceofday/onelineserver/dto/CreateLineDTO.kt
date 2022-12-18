package kr.pieceofday.onelineserver.dto

import kr.pieceofday.onelineserver.enum.LineType

data class CreateLineDTO(
    val title: LineType,
    val content: String
)
