package cz.upce.nnpda.sem_a

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
@EnableAsync
@EnableScheduling
class SemAApplication

fun main(args: Array<String>) {
	runApplication<SemAApplication>(*args)
}
