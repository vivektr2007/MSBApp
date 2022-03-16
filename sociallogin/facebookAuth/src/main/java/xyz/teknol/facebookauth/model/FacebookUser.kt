package xyz.teknol.facebookauth.model

data class FacebookUser(
    val id: String,
    val accessToken: String,
    val email: String,
    val name: String,
    val image: String
)