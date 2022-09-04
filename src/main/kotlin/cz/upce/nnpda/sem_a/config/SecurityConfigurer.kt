package cz.upce.nnpda.sem_a.config

import cz.upce.nnpda.sem_a.auth.JwtConfigurer
import cz.upce.nnpda.sem_a.auth.SkillsAppUserDetailsService
import org.springframework.context.annotation.Bean
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@EnableWebSecurity
@EnableGlobalMethodSecurity(
    prePostEnabled = true,
    securedEnabled = true
)

class SecurityConfigurer(
    val myUserDetailsService: SkillsAppUserDetailsService,
    val jwtTokenConfigurer: JwtConfigurer
) : WebSecurityConfigurerAdapter() {

    @Bean
    override fun authenticationManager(): AuthenticationManager {
        return super.authenticationManager()
    }

    override fun configure(http: HttpSecurity) {
        http.csrf().disable()
            .cors().configurationSource(corsConfigurationSource())
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers("/").permitAll()
            .antMatchers("/").permitAll()
            .antMatchers("/api/v1/auth/login").permitAll()
            .antMatchers("/api/v1/auth/signup").permitAll()
            .antMatchers("/v3/api-docs/**").permitAll()
            .antMatchers("/swagger-ui*/**").permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .apply(jwtTokenConfigurer);
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(myUserDetailsService)
    }

    fun corsConfigurationSource(): CorsConfigurationSource {
        val source = UrlBasedCorsConfigurationSource()
        val corsConfiguration = CorsConfiguration().applyPermitDefaultValues()
        corsConfiguration.addAllowedMethod("DELETE")
        corsConfiguration.addAllowedMethod("PUT")
        source.registerCorsConfiguration("/**", corsConfiguration)
        return source
    }

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder(10)
}