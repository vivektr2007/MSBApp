package xyz.teknol.core.auth.domain

data class VerifyOtpData(
    val message: String?,
    val error: String?,
    val status: Int?,
    val timestamp: String?
)