package cz.upce.nnpda.sem_a.error

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(value = [NNPDAException::class])
    fun handleNotFoundException(e: NNPDAException): ResponseEntity<Any> =
        ResponseEntity.status(e.status).contentType(MediaType.APPLICATION_JSON).body(e.details)
}

