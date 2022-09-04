package cz.upce.nnpda.sem_a.auth

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtTokenFilter(
        val jwtTokenProvider: JwtTokenProvider
) : OncePerRequestFilter() {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        try {
            val token = jwtTokenProvider.resolveToken(request)
            if (token != null && jwtTokenProvider.validateToken(token)) {
                val authentication = jwtTokenProvider.getAuthentication(token)
                authentication.let {
                    SecurityContextHolder.getContext().authentication = authentication
                }
            }
        } catch (ex: JwtAuthenticationException) {
            SecurityContextHolder.clearContext()
            throw JwtAuthenticationException("JWT token is expired or invalid", ex)
        }
        filterChain.doFilter(request, response)
    }
}

