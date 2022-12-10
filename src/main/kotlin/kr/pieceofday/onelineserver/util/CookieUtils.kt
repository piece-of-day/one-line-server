package kr.pieceofday.onelineserver.util

import org.springframework.util.SerializationUtils
import java.util.*
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class CookieUtils {

    companion object {
        val OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME = "oauth2_auth_request"
        val REDIRECT_URI_PARAM_COOKIE_NAME = "redirect_uri"
        val SESSION_COOKIE_NAME = "session_id"
        val COOKIE_EXPIRE_SECONDS = 180

        fun getCookie(request: HttpServletRequest, name: String): Cookie? {
            val cookies = request.cookies
            for (cookie in cookies) {
                if (name.equals(cookie.name)) {
                    return return cookie
                }
            }
            return null
        }

        fun addCookie(response: HttpServletResponse, name: String, value: String, maxAge: Int) {
            val cookie = Cookie(name, value)
            cookie.path = "/"
            cookie.isHttpOnly = true
            cookie.maxAge = maxAge

            response.addCookie(cookie)
        }

        fun deleteCookie(request: HttpServletRequest, response: HttpServletResponse, name: String) {
            val cookies = request.cookies

            cookies?.let {
                for (cookie in cookies) {
                    if (name.equals(cookie.name)) {
                        cookie.value = ""
                        cookie.path = "/"
                        cookie.maxAge = 0
                        response.addCookie(cookie)
                    }
                }
            }
        }

        fun serialize(obj: Any): String {
            return Base64.getUrlEncoder()
                .encodeToString(SerializationUtils.serialize(obj))
        }

        fun <T> deserialize(cookie: Cookie?, cls: Class<T>): T? {
            return cls.cast(
                SerializationUtils.deserialize(
                    Base64.getUrlDecoder().decode(cookie?.value)
                )
            )
        }
    }
}