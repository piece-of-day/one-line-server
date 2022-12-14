package kr.pieceofday.onelineserver.repository

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository

@Repository
class RedisSessionRepository(
    val redisTemplate: RedisTemplate<String, String>
): SessionRepository {
    val valueOperations = redisTemplate.opsForValue()

    override fun findUserPkById(sessionId: String): String? {
        return valueOperations.get(sessionId)
    }

    override fun saveUserPkById(sessionId: String, userId: String) {
        valueOperations.getAndSet("user_id:"+userId, sessionId)?.let {
            redisTemplate.delete("session_id:"+it)
        }
        valueOperations.set("session_id:"+sessionId, userId)
    }

    override fun checkUserPkById(sessionId: String): Boolean {
        val userPkId = valueOperations.get(sessionId)
        return userPkId != null
    }
}