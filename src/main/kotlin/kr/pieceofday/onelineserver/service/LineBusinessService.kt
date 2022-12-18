package kr.pieceofday.onelineserver.service

import kr.pieceofday.onelineserver.domain.Line
import kr.pieceofday.onelineserver.domain.User

interface LineBusinessService {
    fun likedLine(user: User, id: Long): Line
    fun reportLine(user: User, id: Long): Line
    fun readTop10Line()
    fun readLikedLine(user: User)
}