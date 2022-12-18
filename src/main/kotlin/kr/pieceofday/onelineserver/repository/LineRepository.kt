package kr.pieceofday.onelineserver.repository

import kr.pieceofday.onelineserver.domain.Line
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface LineRepository: CrudRepository<Line, Long> {
    fun findTop10ByOrderByLikedDesc(): List<Line>
}
