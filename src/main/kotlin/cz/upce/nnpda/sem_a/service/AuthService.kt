package cz.upce.nnpda.sem_a.service

import cz.upce.nnpda.sem_a.api.AuthenticationRequest
import cz.upce.nnpda.sem_a.api.SignUpRequest
import cz.upce.nnpda.sem_a.api.User
import cz.upce.nnpda.sem_a.auth.JwtTokenProvider
import cz.upce.nnpda.sem_a.auth.NNPDAUser
import cz.upce.nnpda.sem_a.error.NNPDAException
import cz.upce.nnpda.sem_a.persistence.UserEntity
import cz.upce.nnpda.sem_a.persistence.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.servlet.http.HttpServletResponse

@Service
class AuthService(
    val userRepository: UserRepository,
    val jwtTokenProvider: JwtTokenProvider,
)  {
    @Transactional(readOnly = true)
     fun login() {
//        val skillsAppUser = authenticationManager.authenticate(
//                UsernamePasswordAuthenticationToken(request.username, request.password)
//        ).principal as NNPDAUser
//
//        val token = jwtTokenProvider.createToken(username = skillsAppUser.username, null)
//        return User(
//                username = skillsAppUser.email,
//                token = token,
//                firstname = skillsAppUser.firstname,
//                lastname = skillsAppUser.lastname
//        )
    }

     fun signup(request: SignUpRequest) {
//        val userEntity = userRepository.findByUsername(request.username)
//        userEntity?.let {
//            throw NNPDAException("Username already exists", HttpStatus.BAD_REQUEST)
//        }
//         userRepository.saveAndFlush(
//                UserEntity(
//                        username = request.username,
//                        password = passwordEncoder.encode(request.password),
//                        firstname = request.firstname,
//                        lastname = request.lastname,
//                        email = request.email
//                )
//        )
    }
}