package kr.pieceofday.onelineserver.service

import kr.pieceofday.onelineserver.domain.Line
import kr.pieceofday.onelineserver.domain.User

interface LineUtilService {
    fun getLineOrThrowError(id: Long): Line
    fun checkAuthenticationOrThrowError(user: User, line: Line)
}