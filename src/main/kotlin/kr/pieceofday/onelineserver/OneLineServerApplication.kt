package kr.pieceofday.onelineserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class OneLineServerApplication

fun main(args: Array<String>) {
	runApplication<OneLineServerApplication>(*args)
}
