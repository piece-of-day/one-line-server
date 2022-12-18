package kr.pieceofday.onelineserver.controller

import kr.pieceofday.onelineserver.domain.User
import kr.pieceofday.onelineserver.auth.SessionRepository
import kr.pieceofday.onelineserver.util.CookieUtils
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("users")
class UserController(
    val sessionRepository: SessionRepository
) {

    @GetMapping("/signout")
    fun signOut(@AuthenticationPrincipal user: User, request: HttpServletRequest, response: HttpServletResponse): ResponseEntity<Void> {
        sessionRepository.remove(user.id.toString())
        CookieUtils.deleteCookie(request, response, CookieUtils.SESSION_COOKIE_NAME)
        CookieUtils.deleteCookie(request, response, CookieUtils.OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME)
        return ResponseEntity.status(HttpStatus.OK).build()
    }
}