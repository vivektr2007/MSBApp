package xyz.teknol.googleauth.model

data class GoogleUser(
    val id: String,
    val accessToken: String?,
    val email: String,
    val name: String,
    val image: String
)