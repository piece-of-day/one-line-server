package kr.pieceofday.onelineserver.repository

import org.springframework.stereotype.Repository

@Repository
class RedisSessionRepository: SessionRepository {
    override fun findUserPkById(sessionId: String): String {
        TODO("Find UserPk by sessionId")
    }
}