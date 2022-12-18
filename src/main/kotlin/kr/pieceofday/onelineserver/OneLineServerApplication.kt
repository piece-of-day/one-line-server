package kr.pieceofday.onelineserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories
class OneLineServerApplication

fun main(args: Array<String>) {
	runApplication<OneLineServerApplication>(*args)
}
