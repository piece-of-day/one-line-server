package kr.pieceofday.onelineserver.controller

import kr.pieceofday.onelineserver.domain.User
import kr.pieceofday.onelineserver.repository.SessionRepository
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class UserController(
    val sessionRepository: SessionRepository
) {

    @GetMapping("/signout")
    fun signOut(@AuthenticationPrincipal user: User) {
        TODO("sign out process")
    }
}