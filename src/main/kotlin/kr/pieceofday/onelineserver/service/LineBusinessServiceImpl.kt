package kr.pieceofday.onelineserver.service

import kr.pieceofday.onelineserver.domain.Line
import kr.pieceofday.onelineserver.domain.User
import kr.pieceofday.onelineserver.repository.LineRepository

class LineBusinessServiceImpl(
    val lineRepository: LineRepository,
    val lineUtilService: LineUtilService
): LineBusinessService {
    override fun likedLine(user: User, id: Long): Line {
        TODO("Not yet implemented")
    }

    override fun reportLine(user: User, line: Line): Line {
        TODO("Not yet implemented")
    }

    override fun readTop10Line() {
        TODO("Not yet implemented")
    }

    override fun readLikedLine(user: User) {
        TODO("Not yet implemented")
    }
}