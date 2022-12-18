package kr.pieceofday.onelineserver.service

import kr.pieceofday.onelineserver.domain.Line
import kr.pieceofday.onelineserver.domain.User
import kr.pieceofday.onelineserver.dto.CreateLineDTO
import kr.pieceofday.onelineserver.dto.UpdateLineDTO
import kr.pieceofday.onelineserver.repository.LineRepository
import org.springframework.stereotype.Service
import javax.naming.AuthenticationException

@Service
class LineServiceImpl(
    val lineRepository: LineRepository
): LineService {
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
        TODO("Get Random Line for specified Line")
    }

    override fun updateLine(user: User, id: Long, updateLineDto: UpdateLineDTO): Line {
        val line = getLineOrThrowError(id)
        checkAuthenticationOrThrowError(user, line)

        line.title = updateLineDto.title.name
        line.content = updateLineDto.content

        return line
    }

    override fun deleteLine(user: User, id: Long) {
        val line = getLineOrThrowError(id)
        checkAuthenticationOrThrowError(user, line)

        lineRepository.delete(line)
    }

    private fun getLineOrThrowError(id: Long): Line {
        return lineRepository.findById(id).orElseThrow { throw IllegalArgumentException("잘못된 id 값입니다.") }
    }

    private fun checkAuthenticationOrThrowError(user: User, line: Line) {
        if (user.id != line.user.id) {
            throw AuthenticationException("권한이 없습니다.")
        }
    }
}