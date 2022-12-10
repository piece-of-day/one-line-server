package kr.pieceofday.onelineserver.oauth

import kr.pieceofday.onelineserver.repository.SessionRepository
import kr.pieceofday.onelineserver.util.CookieUtils
import org.slf4j.LoggerFactory
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class OAuth2AuthenticationSuccessHandler(
    val sessionRepository: SessionRepository
): SimpleUrlAuthenticationSuccessHandler() {
    val logger = LoggerFactory.getLogger(this::class.java)

    override fun onAuthenticationSuccess(request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication) {
        saveSession(response)
        logger.info("onAuthenticationSuccess")

        val redirectUrl = CookieUtils.getCookie(request, CookieUtils.REDIRECT_URI_PARAM_COOKIE_NAME)?.value

        redirectStrategy.sendRedirect(request, response, redirectUrl)
    }

    private fun saveSession(response: HttpServletResponse?) {
        val sessionId = createSessionId()
        val cookie = Cookie(CookieUtils.SESSION_COOKIE_NAME, sessionId)

        response?.addCookie(cookie)
    }

    private fun createSessionId(): String {
        var check = false
        var sessionId = ""
        while (check == false) {
            val charset = ('a'..'z') + ('A'..'Z') + ('0'..'9')
            sessionId = (1..20)
                .map { charset.random() }
                .joinToString("")
            if (!sessionRepository.checkUserPkById(sessionId)) {
                check = true
            }
        }
        return sessionId
    }
}