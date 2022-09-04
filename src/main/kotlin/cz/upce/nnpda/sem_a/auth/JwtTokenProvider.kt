package cz.upce.nnpda.sem_a.auth

import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.util.*
import javax.annotation.PostConstruct
import javax.servlet.http.HttpServletRequest

private var secretKey = "cz.upce.nnpda_sem_a_isaev"

@Component
class JwtTokenProvider(
        val userDetailsService: SkillsAppUserDetailsService
) {

    @PostConstruct
    private fun init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.toByteArray())
    }

    fun createToken(username: String, roles: Set<String>?): String {
        val claims = Jwts.claims().setSubject(username)
        roles?.let {claims["roles"] = it}
        val now = Date()
        val validity = Date(now.time + 3600000)
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact()

    }

    fun validateToken(token: String): Boolean {
        try {
            return !Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).body.expiration.before(Date())
        } catch (ex: JwtException) {
            throw JwtAuthenticationException( "Token is expired or invalid", ex);
        }
    }

    fun getAuthentication(token: String): Authentication {
        val claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).body
        //val authorities = (claims["roles"]as List<*>).map { SimpleGrantedAuthority(it as String) }
        return UsernamePasswordAuthenticationToken(claims.subject, "", null)
    }

    fun resolveToken(request: HttpServletRequest) = request.getHeader("Authorization")
}