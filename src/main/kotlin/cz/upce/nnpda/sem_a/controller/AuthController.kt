package cz.upce.nnpda.sem_a.controller

import cz.upce.nnpda.sem_a.api.AuthenticationRequest
import cz.upce.nnpda.sem_a.api.SignUpRequest
import cz.upce.nnpda.sem_a.service.AuthService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@CrossOrigin
@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication API", description = "Users authentication")
class AuthController(
        private val authService: AuthService
) {

    @GetMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    fun login() = authService.login()

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    fun logout(
            request: HttpServletRequest,
            response: HttpServletResponse,
            authentication: Authentication
    ) {
        SecurityContextLogoutHandler().logout(request, response, authentication)
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.OK)
    fun signup(
            @RequestBody request: SignUpRequest
    ) = authService.signup(request)
}