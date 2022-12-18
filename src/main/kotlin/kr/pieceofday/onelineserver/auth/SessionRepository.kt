package kr.pieceofday.onelineserver.auth

interface SessionRepository {
    fun findUserPkById(sessionId: String): String?
    fun saveUserPkById(sessionId: String, userId: String)
    fun checkUserPkById(sessionId: String): Boolean
    fun remove(userId: String)
}