package kr.pieceofday.onelineserver.auth

import kr.pieceofday.onelineserver.repository.SessionRepository
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component

@Component
class CookieProvider(
    val customUserDetailService: CustomUserDetailService,
    val sessionRepository: SessionRepository
) {
    fun getAuthentication(sessionId: String): Authentication {
        val userDetails = customUserDetailService.loadUserByUsername(getUserPk(sessionId))
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }

    private fun getUserPk(sessionId: String): String {
        return sessionRepository.findUserPkById(sessionId)
    }
}