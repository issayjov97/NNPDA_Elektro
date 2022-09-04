package cz.upce.nnpda.sem_a.auth

import cz.upce.nnpda.sem_a.service.UserService
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class SkillsAppUserDetailsService(
        val userService: UserService
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val userEntity = userService.getUser(username)
        return NNPDAUser(
                username = userEntity.username,
                password = userEntity.password,
                enabled = userEntity.enabled,
                firstname = userEntity.firstname,
                lastname = userEntity.lastname,
                email = userEntity.email
        )
    }
}
