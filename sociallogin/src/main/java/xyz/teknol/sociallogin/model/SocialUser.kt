package xyz.teknol.sociallogin.model

data class SocialUser(
    val id: String,
    val accessToken: String,
    val email: String,
    val name: String,
    val profileImage: String,
    val type: SocialType
)

enum class SocialType {
    FACEBOOK, GOOGLE
}