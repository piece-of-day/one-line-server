package kr.pieceofday.onelineserver.oauth

import kr.pieceofday.onelineserver.domain.User
import kr.pieceofday.onelineserver.repository.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import java.util.*
import javax.servlet.http.HttpSession

@Service
class OAuthService(
    val userRepository: UserRepository,
    val httpSession: HttpSession
) : OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    val logger = LoggerFactory.getLogger(this::class.java)

    override fun loadUser(userRequest: OAuth2UserRequest?): OAuth2User {
        logger.info("start oauthService")
        val delegate = DefaultOAuth2UserService()
        val oAuth2User = delegate.loadUser(userRequest)

        if (userRequest == null) {
            throw Error("UserRequest is Illegal Option")
        }

        val userNameAttributeName = userRequest
            .clientRegistration
            .providerDetails
            .userInfoEndpoint
            .userNameAttributeName

        val attributes = OAuthAttributes.oauthKakao(
            userNameAttributeName,
            oAuth2User.attributes
        )
        val user = saveOrUpdate(attributes)
        httpSession.setAttribute("user", user.id)


        return DefaultOAuth2User(
            Collections.singleton(SimpleGrantedAuthority("ROLE_USER")),
            attributes.attributes,
            attributes.nameAttributeKey
        )
    }

    private fun saveOrUpdate(attributes: OAuthAttributes): User {
        val user = userRepository.findById(attributes.id).orElse(attributes.toEntity())
        return userRepository.save(user)
    }
}