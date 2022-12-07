package kr.pieceofday.onelineserver.repository

interface SessionRepository {
    fun findUserPkById(sessionId: String): String
}