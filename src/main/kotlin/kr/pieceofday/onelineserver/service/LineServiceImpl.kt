package kr.pieceofday.onelineserver.service

import kr.pieceofday.onelineserver.domain.Line
import kr.pieceofday.onelineserver.domain.User
import kr.pieceofday.onelineserver.dto.CreateLineDTO
import kr.pieceofday.onelineserver.dto.UpdateLineDTO
import kr.pieceofday.onelineserver.repository.LineRepository
import org.springframework.stereotype.Service
import javax.naming.AuthenticationException
import javax.transaction.Transactional

@Service
class LineServiceImpl(
    val lineRepository: LineRepository,
    val lineUtilService: LineUtilService
): LineService {

    @Transactional
    override fun createLine(user: User, createLineDto: CreateLineDTO): Line {
        val line = Line(
            title = createLineDto.title.name,
            content = createLineDto.content,
            liked = 0,
            reported = 0,
            user = user
        )
        return lineRepository.save(line)
    }

    override fun readLine(cnt: Int): List<Line> {
        val seletedLine = lineRepository.selectRandomLine()
        return seletedLine
    }

    @Transactional
    override fun updateLine(user: User, id: Long, updateLineDto: UpdateLineDTO): Line {
        val line = lineUtilService.getLineOrThrowError(id)
        lineUtilService.checkAuthenticationOrThrowError(user, line)

        line.title = updateLineDto.title.name
        line.content = updateLineDto.content

        return line
    }

    @Transactional
    override fun deleteLine(user: User, id: Long) {
        val line = lineUtilService.getLineOrThrowError(id)
        lineUtilService.checkAuthenticationOrThrowError(user, line)

        lineRepository.delete(line)
    }

}