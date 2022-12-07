package kr.pieceofday.onelineserver.oauth

import kr.pieceofday.onelineserver.util.CookieUtils.Companion.COOKIE_EXPIRE_SECONDS
import kr.pieceofday.onelineserver.util.CookieUtils.Companion.OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME
import kr.pieceofday.onelineserver.util.CookieUtils.Companion.REDIRECT_URI_PARAM_COOKIE_NAME
import kr.pieceofday.onelineserver.util.CookieUtils.Companion.SESSION_COOKIE_NAME
import kr.pieceofday.onelineserver.util.CookieUtils.Companion.addCookie
import kr.pieceofday.onelineserver.util.CookieUtils.Companion.deleteCookie
import kr.pieceofday.onelineserver.util.CookieUtils.Companion.deserialize
import kr.pieceofday.onelineserver.util.CookieUtils.Companion.getCookie
import kr.pieceofday.onelineserver.util.CookieUtils.Companion.serialize
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class CustomAuthorizationRequestRepository: AuthorizationRequestRepository<OAuth2AuthorizationRequest> {

    override fun loadAuthorizationRequest(request: HttpServletRequest): OAuth2AuthorizationRequest {
        val authCookie = getCookie(request, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME)
        return deserialize(authCookie!!, OAuth2AuthorizationRequest::class.java)
    }

    override fun saveAuthorizationRequest(authorizationRequest: OAuth2AuthorizationRequest, request: HttpServletRequest, response: HttpServletResponse) {
        if (authorizationRequest == null) {
            deleteCookie(request, response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME)
            deleteCookie(request, response, REDIRECT_URI_PARAM_COOKIE_NAME)
            deleteCookie(request, response, SESSION_COOKIE_NAME)
        }

        addCookie(response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME, serialize(authorizationRequest), COOKIE_EXPIRE_SECONDS)
        request.getParameter(REDIRECT_URI_PARAM_COOKIE_NAME)?.let {
            addCookie(response, REDIRECT_URI_PARAM_COOKIE_NAME, it, COOKIE_EXPIRE_SECONDS)
        }
    }

    override fun removeAuthorizationRequest(request: HttpServletRequest?): OAuth2AuthorizationRequest? {
        return null
    }

}
