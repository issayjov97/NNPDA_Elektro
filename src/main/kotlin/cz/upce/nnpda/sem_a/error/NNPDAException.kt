package cz.upce.nnpda.sem_a.error

import org.springframework.http.HttpStatus
import kotlin.RuntimeException

class NNPDAException(
    val details: String,
    val status: HttpStatus,
    val error: Throwable? = null
): RuntimeException(details, error) {
}