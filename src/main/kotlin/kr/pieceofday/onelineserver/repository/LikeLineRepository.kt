package kr.pieceofday.onelineserver.repository

import kr.pieceofday.onelineserver.domain.LikeLine
import kr.pieceofday.onelineserver.domain.User
import kr.pieceofday.onelineserver.dto.`interface`.Top10LikeInterface
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface LikeLineRepository: CrudRepository<LikeLine, Long> {
    @Query(value = "select " +
        "line_id as LineId, " +
        "count(user_id) as Liked " +
        "from like_line " +
        "group by LineId " +
        "order by Liked desc " +
        "limit 10", nativeQuery = true)
    fun readTop10ListAboutLikeLine(): List<Top10LikeInterface>

    fun findByUser(user: User): List<LikeLine>
}