package kr.pieceofday.onelineserver.controller

import kr.pieceofday.onelineserver.domain.User
import kr.pieceofday.onelineserver.dto.ResponseLineDTO
import kr.pieceofday.onelineserver.service.LineBusinessService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("line")
class LineBusinessController(
    val lineBusinessService: LineBusinessService
) {

    @PostMapping("{id}/like")
    fun likeLine(@AuthenticationPrincipal user: User, @PathVariable(value = "id") id: Long ): ResponseEntity<ResponseLineDTO> {
        return ResponseEntity.status(HttpStatus.OK).body(
            ResponseLineDTO.makeDTO(lineBusinessService.likedLine(user, id))
        )
    }

    @GetMapping("rank")
    fun readTop10LikeLine(@RequestParam(value = "keyword", required = false, defaultValue = "") keyword: String): ResponseEntity<List<ResponseLineDTO>> {
        return ResponseEntity.status(HttpStatus.OK).body(
            lineBusinessService.readTop10Line().map { ResponseLineDTO.makeDTO(it) }
        )
    }
}