package kr.pieceofday.onelineserver.oauth

import kr.pieceofday.onelineserver.auth.SessionRepository
import kr.pieceofday.onelineserver.util.CookieUtils.Companion.COOKIE_EXPIRE_SECONDS
import kr.pieceofday.onelineserver.util.CookieUtils.Companion.OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME
import kr.pieceofday.onelineserver.util.CookieUtils.Companion.REDIRECT_URI_PARAM_COOKIE_NAME
import kr.pieceofday.onelineserver.util.CookieUtils.Companion.SESSION_COOKIE_NAME
import kr.pieceofday.onelineserver.util.CookieUtils.Companion.addCookie
import kr.pieceofday.onelineserver.util.CookieUtils.Companion.deleteCookie
import kr.pieceofday.onelineserver.util.CookieUtils.Companion.deserialize
import kr.pieceofday.onelineserver.util.CookieUtils.Companion.getCookie
import kr.pieceofday.onelineserver.util.CookieUtils.Companion.serialize
import org.slf4j.LoggerFactory
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class CustomAuthorizationRequestRepository(
    val sessionRepository: SessionRepository
): AuthorizationRequestRepository<OAuth2AuthorizationRequest> {
    val logger = LoggerFactory.getLogger(this::class.java)

    override fun loadAuthorizationRequest(request: HttpServletRequest): OAuth2AuthorizationRequest? {
        logger.info("loadAuthorizationRequest")
        val authCookie = getCookie(request, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME)
        return deserialize(authCookie, OAuth2AuthorizationRequest::class.java)
    }

    override fun saveAuthorizationRequest(authorizationRequest: OAuth2AuthorizationRequest, request: HttpServletRequest, response: HttpServletResponse) {
        logger.info("saveAuthorizationRequest")

        if (authorizationRequest == null) {
            logger.info("authorizationRequest")
            deleteCookie(request, response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME)
            deleteCookie(request, response, REDIRECT_URI_PARAM_COOKIE_NAME)
            deleteCookie(request, response, SESSION_COOKIE_NAME)
            return
        }

        addCookie(response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME, serialize(authorizationRequest), COOKIE_EXPIRE_SECONDS)

        request.getParameter(REDIRECT_URI_PARAM_COOKIE_NAME)?.let {
            addCookie(response, REDIRECT_URI_PARAM_COOKIE_NAME, it, COOKIE_EXPIRE_SECONDS)
        }
    }

    override fun removeAuthorizationRequest(request: HttpServletRequest): OAuth2AuthorizationRequest? {
        logger.info("removeAuthorizationRequest")

        return this.loadAuthorizationRequest(request)
    }
}
