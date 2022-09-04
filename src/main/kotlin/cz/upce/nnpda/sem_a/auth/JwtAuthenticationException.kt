package cz.upce.nnpda.sem_a.auth

import org.springframework.security.core.AuthenticationException

class JwtAuthenticationException(
        msg: String?,
        cause: Throwable?
) : AuthenticationException(msg, cause)