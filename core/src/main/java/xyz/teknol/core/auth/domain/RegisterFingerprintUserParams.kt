package xyz.teknol.core.auth.domain

data class RegisterFingerprintUserParams(
    val fingerprint: String,
    val mobileNo: Long,
    val msbPin: Int
)
