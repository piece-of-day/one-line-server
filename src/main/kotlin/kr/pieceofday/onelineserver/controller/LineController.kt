package kr.pieceofday.onelineserver.controller

import kr.pieceofday.onelineserver.domain.User
import kr.pieceofday.onelineserver.dto.CreateLineDTO
import kr.pieceofday.onelineserver.dto.ResponseLineDTO
import kr.pieceofday.onelineserver.dto.UpdateLineDTO
import kr.pieceofday.onelineserver.service.LineService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("line")
class LineController(
    val lineService: LineService
) {

    @PostMapping("")
    fun createLine(@AuthenticationPrincipal user: User, @RequestBody createLineDto: CreateLineDTO): ResponseEntity<ResponseLineDTO> {
        println(createLineDto)
        return ResponseEntity.status(HttpStatus.CREATED).body(
            ResponseLineDTO.makeDTO(lineService.createLine(user, createLineDto))
        )
    }

    @GetMapping("")
    fun readLine(@RequestParam(value = "cnt", required = false, defaultValue = "3") cnt: Int): ResponseEntity<List<ResponseLineDTO>> {
        return ResponseEntity.status(HttpStatus.OK).body(
            lineService.readLine(cnt).map { ResponseLineDTO.makeDTO(it) }
        )
    }

    @PatchMapping("{id}")
    fun updateLine(@AuthenticationPrincipal user: User, @PathVariable(value = "id") id: Long, @RequestBody updateLineDTO: UpdateLineDTO): ResponseEntity<ResponseLineDTO> {
        return ResponseEntity.status(HttpStatus.OK).body(
            ResponseLineDTO.makeDTO(lineService.updateLine(user, id, updateLineDTO))
        )
    }

    @DeleteMapping("{id}")
    fun deleteLine(@AuthenticationPrincipal user: User, @PathVariable(value = "id") id: Long): ResponseEntity<Unit> {
        lineService.deleteLine(user, id)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

}