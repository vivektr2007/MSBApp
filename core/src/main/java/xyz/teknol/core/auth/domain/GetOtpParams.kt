package xyz.teknol.core.auth.domain

data class GetOtpParams(
    val mobileNo: String,
    val stdCode: String,
)