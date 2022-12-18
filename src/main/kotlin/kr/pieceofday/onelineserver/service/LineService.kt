package kr.pieceofday.onelineserver.service

import kr.pieceofday.onelineserver.domain.Line
import kr.pieceofday.onelineserver.domain.User
import kr.pieceofday.onelineserver.dto.CreateLineDTO
import kr.pieceofday.onelineserver.dto.UpdateLineDTO

interface LineService {
    fun createLine(user: User, createLineDto: CreateLineDTO): Line
    fun readLine(cnt: Int): List<Line>
    fun updateLine(user: User, id: Long, updateLineDto: UpdateLineDTO): Line
    fun deleteLine(user: User, id: Long)
}