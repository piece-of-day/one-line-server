package kr.pieceofday.onelineserver.dto

import kr.pieceofday.onelineserver.domain.Line

data class ResponseLineDTO(
    val id: Long?,
    val user_id: Long?,
    val title: String?,
    val content: String?,
    val liked: Long?
) {
    companion object {
        fun makeDTO(line: Line): ResponseLineDTO {
            return ResponseLineDTO(
                id = line.id,
                user_id = line.user.id,
                title = line.title,
                content = line.content,
                liked = line.liked
            )
        }
    }
}
