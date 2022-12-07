package kr.pieceofday.onelineserver.config

import kr.pieceofday.onelineserver.oauth.CustomAuthorizationRequestRepository
import kr.pieceofday.onelineserver.oauth.OAuth2AuthenticationSuccessHandler
import kr.pieceofday.onelineserver.oauth.OAuthService
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain

@EnableWebSecurity
class SecurityConfig(
    val oAuthService: OAuthService
) {

    fun customAuthorizationRequestRepository(): CustomAuthorizationRequestRepository {
        return CustomAuthorizationRequestRepository()
    }

    @Bean
    fun oAuth2AuthenticationSuccessHandler(): OAuth2AuthenticationSuccessHandler {
        TODO("SuccessHandler 작성")
        return OAuth2AuthenticationSuccessHandler()
    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf().disable()
            .headers().frameOptions().disable()
            .and()
                .authorizeRequests()
                .antMatchers("/**").permitAll()
            .and()
            .oauth2Login()
            .authorizationEndpoint()
                .authorizationRequestRepository(customAuthorizationRequestRepository())
            .and()
                .successHandler()
                .userInfoEndpoint() // oauth 성공 이후 설정 시작
//                .userService() 여기서 셋팅 -> 아이디 값
        return http.build()
    }
}