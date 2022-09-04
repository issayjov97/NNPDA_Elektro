package cz.upce.nnpda.sem_a.controller

import cz.upce.nnpda.sem_a.api.ChangePasswordRequest
import cz.upce.nnpda.sem_a.api.OTPCheckRequest
import cz.upce.nnpda.sem_a.service.UserService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@CrossOrigin
@RestController
@RequestMapping("api/v1/users")
@Tag(name = "User API", description = "User information")
class UserController(
        private val userService: UserService
) {

    @GetMapping("/otp")
    fun generateOTP(
         authentication: Authentication
    ) = userService.generateOTP(authentication)

    @PostMapping("/password")
    fun changePassword(
        @RequestBody request: ChangePasswordRequest,
        authentication: Authentication
    ) = userService.changePassword(authentication, request)

    @PostMapping("/check")
    fun checkOTP(
        @RequestBody otpCheckRequest: OTPCheckRequest,
        authentication: Authentication
    ) = userService.checkOTP(authentication, otpCheckRequest)
}