package kr.pieceofday.onelineserver.oauth

import java.util.*

data class OAuthAttributes(
    val attributes: Map<String, Objects>,
    val nameAttributeKey: String,
    val name: String
) {
    companion object {
        fun oauthKakao(userNameAttributeName: String, attributes: Map<String, Objects>) : OAuthAttributes {
            val kakaoAccount = attributes["kakao_account"] as Map<String, Objects>
            val kakaoProfile = kakaoAccount["profile"] as Map<String, Objects>
            return OAuthAttributes(
                attributes = attributes,
                nameAttributeKey = userNameAttributeName,
                name = kakaoProfile["nickname"] as String
            )
        }
    }

}
