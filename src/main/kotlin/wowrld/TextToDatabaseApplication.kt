package wowrld

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TextToDatabaseApplication

fun main(args: Array<String>) {
	runApplication<TextToDatabaseApplication>(*args)
}
