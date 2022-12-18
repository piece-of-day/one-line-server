package kr.pieceofday.onelineserver.auth

import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication

@Configuration
class CookieProvider(
    val customUserDetailService: CustomUserDetailService,
    val sessionRepository: SessionRepository
) {
    fun getAuthentication(sessionId: String): Authentication? {
        val userPk = getUserPk(sessionId) ?: return null
        val userDetails = customUserDetailService.loadUserByUsername(userPk)
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }

    private fun getUserPk(sessionId: String): String? {
        return sessionRepository.findUserPkById(sessionId)
    }
}