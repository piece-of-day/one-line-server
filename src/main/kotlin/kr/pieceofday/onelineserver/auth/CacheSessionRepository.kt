package kr.pieceofday.onelineserver.auth

import kr.pieceofday.onelineserver.auth.SessionRepository

class CacheSessionRepository(
): SessionRepository {
    val userSessionMap = mutableMapOf<String, String>()

    override fun findUserPkById(sessionId: String): String {
        return userSessionMap[sessionId] ?: "null"
    }

    override fun saveUserPkById(sessionId: String, userId: String) {
        userSessionMap[sessionId] = userId
    }

    override fun checkUserPkById(sessionId: String): Boolean {
        return userSessionMap.containsKey(sessionId)
    }

    override fun remove(userId: String) {
        TODO("Not yet implemented")
    }

}