//package cz.addai.kc.auth
//
//import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver
//import org.keycloak.adapters.springsecurity.KeycloakSecurityComponents
//import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider
//import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.ComponentScan
//import org.springframework.context.annotation.Configuration
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
//import org.springframework.security.config.annotation.web.builders.HttpSecurity
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
//import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper
//import org.springframework.security.core.session.SessionRegistryImpl
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
//import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy
//import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy
//import org.springframework.web.cors.CorsConfiguration
//import org.springframework.web.cors.CorsConfigurationSource
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource
//
//
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//@ComponentScan(basePackageClasses = [KeycloakSecurityComponents::class])
//class WebSecurityConfig : KeycloakWebSecurityConfigurerAdapter() {
//
//    @Bean
//    fun keycloakConfigResolver(): KeycloakSpringBootConfigResolver {
//        return KeycloakSpringBootConfigResolver()
//    }
//
//    override fun configure(auth: AuthenticationManagerBuilder) {
//        auth.authenticationProvider(
//            KeycloakAuthenticationProvider().apply {
//                this.setGrantedAuthoritiesMapper(SimpleAuthorityMapper())
//            }
//        )
//    }
//
//    @Throws(Exception::class)
//    override fun configure(http: HttpSecurity) {
//        http.authorizeRequests()
//            .antMatchers("/").hasRole("USER")
//            .anyRequest().permitAll();
//    }
//
//    @Bean
//    fun passwordEncoder() = BCryptPasswordEncoder(10)
//
//    override fun sessionAuthenticationStrategy(): SessionAuthenticationStrategy {
//        return RegisterSessionAuthenticationStrategy(SessionRegistryImpl())
//    }
//
//
//    fun corsConfigurationSource(): CorsConfigurationSource {
//        val source = UrlBasedCorsConfigurationSource()
//        val corsConfiguration = CorsConfiguration().applyPermitDefaultValues()
//        corsConfiguration.addAllowedMethod("DELETE")
//        corsConfiguration.addAllowedMethod("PUT")
//        source.registerCorsConfiguration("/**", corsConfiguration)
//        return source
//    }
//}