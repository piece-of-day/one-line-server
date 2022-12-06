package kr.pieceofday.onelineserver.oauth

import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import java.util.*

@Service
class OAuthService : OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    override fun loadUser(userRequest: OAuth2UserRequest?): OAuth2User {
        println(1)
        val delegate = DefaultOAuth2UserService()
        val oAuth2User = delegate.loadUser(userRequest)

        if (userRequest == null) {
            throw Error("UserRequest is Illegal Option")
        }
        val registrationId = userRequest.clientRegistration.registrationId
        val userNameAttributeName = userRequest
            .clientRegistration
            .providerDetails
            .userInfoEndpoint
            .userNameAttributeName
        val attributes = oAuth2User.attributes
        println(2)

//        TODO("User Domain 작성")
        println(attributes)
        println(userNameAttributeName)
        println(registrationId)
        println(DefaultOAuth2User(
                Collections.singleton(SimpleGrantedAuthority("ROLE_USER")),
                attributes,
                userNameAttributeName
        ))

        TODO("User DB에 저장 및 업데이트")

        return DefaultOAuth2User(
            Collections.singleton(SimpleGrantedAuthority("ROLE_USER")),
            attributes,
            userNameAttributeName
        )
    }
}