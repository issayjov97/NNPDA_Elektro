package cz.upce.nnpda.sem_a.service

import cz.upce.nnpda.sem_a.api.ChangePasswordRequest
import cz.upce.nnpda.sem_a.api.OTPCheckRequest
import cz.upce.nnpda.sem_a.error.NNPDAException
import cz.upce.nnpda.sem_a.persistence.OTPRepository
import cz.upce.nnpda.sem_a.persistence.OneTimePasswordEntity
import cz.upce.nnpda.sem_a.persistence.UserRepository
import net.bytebuddy.utility.RandomString
import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class UserService(
    private val userRepository: UserRepository,
    private val otpRepository: OTPRepository,
    private val notificationService: NotificationService
) {
    @Transactional(readOnly = true)
    fun getUser(username: String) = userRepository.findByUsername(username)
        ?: throw NNPDAException("User was not found", HttpStatus.NOT_FOUND)


    fun changePassword(authentication: Authentication, request: ChangePasswordRequest) {
        val bCryptPasswordEncoder = BCryptPasswordEncoder()
        val userEntity = getUser(authentication.principal as String)
        if (userEntity.otp_required)
            throw NNPDAException("One time password is required", HttpStatus.UNAUTHORIZED)

        if (!bCryptPasswordEncoder.matches(request.currentPassword, userEntity.password))
            throw NNPDAException("Wrong password", HttpStatus.NOT_FOUND)
        userEntity.password = bCryptPasswordEncoder.encode(request.newPassword)
        userEntity.otp_required = true
        userRepository.saveAndFlush(userEntity)
    }

    fun checkOTP(authentication: Authentication, otpCheckRequest: OTPCheckRequest) {
        val userEntity = getUser(authentication.principal as String)
        userEntity.isOTPValid(otpCheckRequest.otp)
        userEntity.otp_required = false
        userEntity.oneTimePassword = null
        userRepository.saveAndFlush(userEntity)
    }

    fun generateOTP(authentication: Authentication) {
        val userEntity = getUser(authentication.principal as String)
        val oneTimePassword = OneTimePasswordEntity(
            id = userEntity.id,
            createdAt = Date(),
            userEntity = userEntity,
            value = generateOneTimePassword()
        )
        userEntity.oneTimePassword = oneTimePassword
        userRepository.saveAndFlush(userEntity)
        notificationService.sendEmail("One time password: ${oneTimePassword.value}", userEntity.email)
    }

    private fun generateOneTimePassword() = RandomString.make(32)
}