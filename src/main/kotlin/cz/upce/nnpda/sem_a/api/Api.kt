package cz.upce.nnpda.sem_a.api

import com.fasterxml.jackson.annotation.JsonProperty

data class ChangePasswordRequest(
    val currentPassword: String,
    val newPassword: String
)

data class SensorApi(
    val id: Long? = null,
    val type: String,
    val measurementUnit: String
)


data class OTPCheckRequest(
    val otp: String,
)

data class AuthenticationRequest(
    @JsonProperty("email")
    val username: String,
    val password: String
)

data class User(
    val username: String,
    val token: String,
    val authorities: List<String>? = emptyList(),
    val firstname: String,
    val lastname: String
)

data class SignUpRequest(
    val firstname: String,
    val lastname: String,
    val username: String,
    val email: String,
    val password: String
)