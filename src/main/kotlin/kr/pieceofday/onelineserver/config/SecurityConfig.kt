package kr.pieceofday.onelineserver.config

import kr.pieceofday.onelineserver.oauth.OAuthService
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain

@EnableWebSecurity
class SecurityConfig(
    val oAuthService: OAuthService
) {

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
                        .userInfoEndpoint()
                            .userService(oAuthService)
        return http.build()
    }
}