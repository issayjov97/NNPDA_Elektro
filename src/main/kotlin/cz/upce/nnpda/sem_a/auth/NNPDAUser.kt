package cz.upce.nnpda.sem_a.auth

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class NNPDAUser(
        private val username: String,
        private val password: String,
        private val enabled: Boolean,
        private val authorities: Set<GrantedAuthority> = emptySet(),
        val firstname: String,
        val lastname: String,
        val email: String,

) : UserDetails {
    override fun getAuthorities() = authorities

    override fun getPassword() = password

    override fun getUsername() = username

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true

    override fun isCredentialsNonExpired() = true

    override fun isEnabled() = enabled
}