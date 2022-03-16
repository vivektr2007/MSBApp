package xyz.teknol.core.auth.domain

data class GetOtpData(
    val createdAt: String?,
    val id: String?,
    val mobileNumber: Long?,
    val otp: String?
)