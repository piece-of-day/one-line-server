package kr.pieceofday.onelineserver.auth

import kr.pieceofday.onelineserver.util.CookieUtils
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Configuration
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Configuration
class AuthenticationFilter(
    val cookieProvider: CookieProvider
): OncePerRequestFilter() {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        logger.info("doFileterInternal")

        getSessionIdAtRequest(request)?.let {
            val authentication = cookieProvider.getAuthentication(it)
            logger.info(authentication)
            SecurityContextHolder.getContext().authentication = authentication
        }
        filterChain.doFilter(request, response)
    }

    private fun getSessionIdAtRequest(request: HttpServletRequest): String? {
        return CookieUtils.getCookie(request, CookieUtils.SESSION_COOKIE_NAME)?.value
    }
}