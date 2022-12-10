package kr.pieceofday.onelineserver.config

import kr.pieceofday.onelineserver.oauth.CustomAuthorizationRequestRepository
import kr.pieceofday.onelineserver.oauth.OAuth2AuthenticationSuccessHandler
import kr.pieceofday.onelineserver.oauth.OAuthService
import kr.pieceofday.onelineserver.repository.SessionRepository
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain

@EnableWebSecurity
class SecurityConfig(
    val oAuthService: OAuthService,
    val sessionRepository: SessionRepository
) {

    fun customAuthorizationRequestRepository(): CustomAuthorizationRequestRepository {
        return CustomAuthorizationRequestRepository()
    }

    @Bean
    fun oAuth2AuthenticationSuccessHandler(): OAuth2AuthenticationSuccessHandler {
        return OAuth2AuthenticationSuccessHandler(sessionRepository)
    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf().disable()
            .headers().frameOptions().disable()
            .and()
                .authorizeRequests()
                .antMatchers("/hello").hasRole("USER")
                .antMatchers("/").permitAll()
            .and()
                .oauth2Login()
                    .userInfoEndpoint()
                        .userService(oAuthService)
            .and()
            .authorizationEndpoint()
                .authorizationRequestRepository(customAuthorizationRequestRepository())
            .and()
                .successHandler(oAuth2AuthenticationSuccessHandler())

        return http.build()
    }
}