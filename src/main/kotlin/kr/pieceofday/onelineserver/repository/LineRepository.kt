package kr.pieceofday.onelineserver.repository

import kr.pieceofday.onelineserver.domain.Line
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface LineRepository: CrudRepository<Line, Long> {
    fun findTop10ByOrderByLikedDesc(): List<Line>

    @Query(value = "select * from line order by rand() limit 3", nativeQuery = true)
    fun selectRandomLine(): List<Line>
}
