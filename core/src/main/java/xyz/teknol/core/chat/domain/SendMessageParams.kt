package xyz.teknol.core.chat.domain

data class SendMessageParams(
    val createdAt: String?,
    val fromUserId: String?,
    val message: Message?,
    val toUserId: String?
) {
    data class Message(
        val message: String?,
        val timestamp: Int?,
        val userName: String?
    )
}