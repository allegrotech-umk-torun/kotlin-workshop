package pl.allegroumk.allediet

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AlledietApplication

fun main(args: Array<String>) {
	runApplication<AlledietApplication>(*args)
}
