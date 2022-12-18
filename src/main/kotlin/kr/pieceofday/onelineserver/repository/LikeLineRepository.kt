package kr.pieceofday.onelineserver.repository

import kr.pieceofday.onelineserver.domain.LikeLine
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface LikeLineRepository: CrudRepository<LikeLine, Long>