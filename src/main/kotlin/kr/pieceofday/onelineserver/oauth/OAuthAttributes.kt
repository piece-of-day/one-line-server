package kr.pieceofday.onelineserver.oauth

import kr.pieceofday.onelineserver.domain.User

data class OAuthAttributes(
    val attributes: MutableMap<String, Any>,
    val nameAttributeKey: String,
    val id: Long,
    val name: String
) {
    companion object {
        fun oauthKakao(userNameAttributes: String, attributes: MutableMap<String, Any>) : OAuthAttributes {
            val kakaoAccount = attributes["kakao_account"] as Map<String, Any?>
            val kakaoProfile = kakaoAccount["profile"] as Map<String, Any?>

            return OAuthAttributes(
                attributes = attributes,
                nameAttributeKey = userNameAttributes,
                id = attributes["id"] as Long,
                name = kakaoProfile["nickname"] as String
            )
        }
    }
}

fun OAuthAttributes.toEntity(): User {
    return User(
        id = this.id,
        name = this.name
    )
}
