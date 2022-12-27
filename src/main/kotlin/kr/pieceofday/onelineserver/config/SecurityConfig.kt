package kr.pieceofday.onelineserver.config

import kr.pieceofday.onelineserver.oauth.CustomAuthorizationRequestRepository
import kr.pieceofday.onelineserver.oauth.OAuth2AuthenticationSuccessHandler
import kr.pieceofday.onelineserver.oauth.OAuthService
import kr.pieceofday.onelineserver.auth.SessionRepository
import kr.pieceofday.onelineserver.repository.LikeLineRepository
import kr.pieceofday.onelineserver.repository.LineRepository
import kr.pieceofday.onelineserver.repository.UserRepository
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@EnableWebSecurity
class SecurityConfig(
    val oAuthService: OAuthService,
    val sessionRepository: SessionRepository,
    val likeLineRepository: LikeLineRepository,
    val userRepository: UserRepository,
    val lineRepository: LineRepository
) {

    fun customAuthorizationRequestRepository(): CustomAuthorizationRequestRepository {
        return CustomAuthorizationRequestRepository(sessionRepository)
    }

    @Bean
    fun oAuth2AuthenticationSuccessHandler(): OAuth2AuthenticationSuccessHandler {
        return OAuth2AuthenticationSuccessHandler(sessionRepository, likeLineRepository, userRepository, lineRepository)
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = listOf("http://localhost:3000", "https://today-one-line.vercel.app")
        configuration.allowedMethods = listOf("*")
        configuration.allowedHeaders = listOf("Authorization", "Cache-Control", "Content-Type")
        configuration.allowCredentials = true
        configuration.maxAge = 3600L
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .cors().and()
            .csrf().disable()
            .headers().frameOptions().disable()
            .and()
                .authorizeRequests()
                .antMatchers("/hello").hasRole("USER")
                .antMatchers("/").permitAll()
            .and()
            .oauth2Login()
            .authorizationEndpoint()
                .authorizationRequestRepository(customAuthorizationRequestRepository())
            .and()
                .successHandler(oAuth2AuthenticationSuccessHandler())
                .userInfoEndpoint()
                .userService(oAuthService)

        return http.build()
    }
}