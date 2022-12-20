package kr.pieceofday.onelineserver.service

import kr.pieceofday.onelineserver.domain.LikeLine
import kr.pieceofday.onelineserver.domain.Line
import kr.pieceofday.onelineserver.domain.User
import kr.pieceofday.onelineserver.repository.LikeLineRepository
import kr.pieceofday.onelineserver.repository.LineRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class LineBusinessServiceImpl(
    val lineRepository: LineRepository,
    val likeLineRepository: LikeLineRepository,
    val lineUtilService: LineUtilService
): LineBusinessService {

    @Transactional
    override fun likedLine(user: User, id: Long): Line {
        val line = lineUtilService.getLineOrThrowError(id)

        val likeLine = LikeLine(
            user = user,
            line = line
        )

        likeLineRepository.save(likeLine)
        line.liked += 1
        return line
    }

    @Transactional
    override fun reportLine(user: User, id: Long): Line {
        val line = lineUtilService.getLineOrThrowError(id)

        line.reported += 1
        if (line.reported >= 5) {
            TODO("Implement Line Delete And after process")
        }
        return line
    }

    override fun readTop10Line(): List<Line> {
        return lineRepository.findTop10ByOrderByLikedDesc()
    }

    override fun readLikedLine(user: User): List<Line> {
        return likeLineRepository.findByUser(user)
            .map { it.line }
            .toSet().toList()
    }
}