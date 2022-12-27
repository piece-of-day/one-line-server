package kr.pieceofday.onelineserver.oauth

import kr.pieceofday.onelineserver.auth.SessionRepository
import kr.pieceofday.onelineserver.domain.LikeLine
import kr.pieceofday.onelineserver.repository.LikeLineRepository
import kr.pieceofday.onelineserver.repository.LineRepository
import kr.pieceofday.onelineserver.repository.UserRepository
import kr.pieceofday.onelineserver.util.CookieUtils
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class OAuth2AuthenticationSuccessHandler(
    val sessionRepository: SessionRepository,
    val likeLineRepository: LikeLineRepository,
    val userRepository: UserRepository,
    val lineRepository: LineRepository
): SimpleUrlAuthenticationSuccessHandler() {
    val logger = LoggerFactory.getLogger(this::class.java)

    @Value("\${oauth.default.redirect}")
    lateinit var rootRedirectUrl: String

    override fun onAuthenticationSuccess(request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication) {
        val userId = saveSessionAndReturnId(response, authentication)
        logger.info("onAuthenticationSuccess: request_id = ${userId}")

        saveLikeForUser(request, userId)

        val redirectUrl = CookieUtils.getCookie(request, CookieUtils.REDIRECT_URI_PARAM_COOKIE_NAME)?.value ?: rootRedirectUrl

        redirectStrategy.sendRedirect(request, response, redirectUrl)
    }

    private fun saveLikeForUser(request: HttpServletRequest, userId: String) {
        val lineId = request.cookies.firstOrNull { it.name == "line_id" }?.value
        if (lineId == null) {
            return
        }
        val user = userRepository.findById(userId.toLong()).get()
        val line = lineRepository.findById(lineId.toLong()).get()
        val likeLine = likeLineRepository.findByUserAndLine(user, line).orElse(LikeLine(user = user, line = line))
        likeLineRepository.save(likeLine)
    }

    private fun saveSessionAndReturnId(response: HttpServletResponse?, authentication: Authentication): String {
        val sessionId = createSessionId()

        CookieUtils.addCookie(response!!, CookieUtils.SESSION_COOKIE_NAME, sessionId)
        val aa = authentication.principal as OAuth2User
        val userId = aa.attributes["id"].toString()
        sessionRepository.saveUserPkById(sessionId, userId)
        return userId
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