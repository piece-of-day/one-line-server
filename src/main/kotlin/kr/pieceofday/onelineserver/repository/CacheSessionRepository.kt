package kr.pieceofday.onelineserver.repository

class CacheSessionRepository: SessionRepository {
    override fun findUserPkById(sessionId: String): String {
        TODO("Not yet implemented")
    }

    override fun saveUserPkById(sessionId: String, userId: String) {
        TODO("Not yet implemented")
    }

    override fun checkUserPkById(sessionId: String): Boolean {
        TODO("Not yet implemented")
    }

}