package xyz.teknol.core.profile.domain

data class UpdateAnonymousProfileParams(
    val anonymousUserId: String?,
    val anonymousUserNearby: Boolean?,
    val id: String?,
    val profilePic: String?,
    val revealGender: Boolean?,
    val smsCountPerUser: String?
)