package kr.pieceofday.onelineserver.service

import kr.pieceofday.onelineserver.domain.Line
import kr.pieceofday.onelineserver.domain.User
import kr.pieceofday.onelineserver.dto.CreateLineDTO
import kr.pieceofday.onelineserver.dto.UpdateLineDTO
import kr.pieceofday.onelineserver.repository.LineRepository
import org.springframework.stereotype.Service

@Service
class LineServiceImpl(
    val lineRepository: LineRepository
): LineService {
    override fun createLine(user: User, createLineDto: CreateLineDTO): Line {
        TODO("Not yet implemented")
    }

    override fun readLine(cnt: Int): List<Line> {
        TODO("Not yet implemented")
    }

    override fun updateLine(user: User, id: Long, updateLineDto: UpdateLineDTO): Line {
        TODO("Not yet implemented")
    }

    override fun deleteLine(user: User, id: Long) {
        TODO("Not yet implemented")
    }
}