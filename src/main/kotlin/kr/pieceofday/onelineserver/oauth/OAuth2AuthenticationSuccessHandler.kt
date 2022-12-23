package kr.pieceofday.onelineserver.oauth

import kr.pieceofday.onelineserver.auth.SessionRepository
import kr.pieceofday.onelineserver.util.CookieUtils
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class OAuth2AuthenticationSuccessHandler(
    val sessionRepository: SessionRepository
): SimpleUrlAuthenticationSuccessHandler() {
    val logger = LoggerFactory.getLogger(this::class.java)

    @Value("\${oauth.default.redirect}")
    lateinit var rootRedirectUrl: String

    override fun onAuthenticationSuccess(request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication) {
        logger.info("onAuthenticationSuccess")
        saveSession(response, authentication)

        val redirectUrl = CookieUtils.getCookie(request, CookieUtils.REDIRECT_URI_PARAM_COOKIE_NAME)?.value ?: rootRedirectUrl

        redirectStrategy.sendRedirect(request, response, redirectUrl)
    }

    private fun saveSession(response: HttpServletResponse?, authentication: Authentication) {
        val sessionId = createSessionId()

        CookieUtils.addCookie(response!!, CookieUtils.SESSION_COOKIE_NAME, sessionId)
        val aa = authentication.principal as OAuth2User
        println(aa.attributes["id"])
        sessionRepository.saveUserPkById(sessionId, aa.attributes["id"].toString())

        logger.info(CookieUtils.serialize(sessionId))
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