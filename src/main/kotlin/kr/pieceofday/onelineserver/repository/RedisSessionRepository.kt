package kr.pieceofday.onelineserver.repository

import org.springframework.stereotype.Repository

class RedisSessionRepository: SessionRepository {
    override fun findUserPkById(sessionId: String): String {
        TODO("Find UserPk by sessionId")
    }

    override fun saveUserPkById(sessionId: String, userId: String) {
        TODO("Not yet implemented")
    }

    override fun checkUserPkById(sessionId: String): Boolean {
        TODO("Not yet implemented")
    }
}