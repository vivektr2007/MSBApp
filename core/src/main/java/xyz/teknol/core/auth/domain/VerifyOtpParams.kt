package xyz.teknol.core.auth.domain

data class VerifyOtpParams(
    val otp: String,
    val mobileNumber: String,
    val stdCode: String,
)