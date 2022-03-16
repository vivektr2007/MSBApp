package xyz.teknol.core.profile.domain

 data class AnonymousProfileData(
    val anonymousUserId: String?,
    val anonymousUserNearby: Boolean?,
    val createdAt: String?,
    val id: String?,
    val profilePic: String?,
    val profilePicFullUrl: String?,
    val realUserId: String?,
    val revealGender: Boolean?,
    val smsCountPerUser: String?,
    val updatedAt: String?
)