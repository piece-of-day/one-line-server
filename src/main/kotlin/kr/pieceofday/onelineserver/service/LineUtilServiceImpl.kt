package kr.pieceofday.onelineserver.service

import kr.pieceofday.onelineserver.domain.Line
import kr.pieceofday.onelineserver.domain.User
import kr.pieceofday.onelineserver.repository.LineRepository
import org.springframework.stereotype.Service
import javax.naming.AuthenticationException

@Service
class LineUtilServiceImpl(
    val lineRepository: LineRepository
): LineUtilService {
    override fun getLineOrThrowError(id: Long): Line {
        return lineRepository.findById(id).orElseThrow { throw IllegalArgumentException("잘못된 id 값입니다.") }
    }

    override fun checkAuthenticationOrThrowError(user: User, line: Line) {
        if (user.id != line.user.id) {
            throw AuthenticationException("권한이 없습니다.")
        }
    }
}