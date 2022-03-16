package xyz.teknol.core.auth.domain

data class SignUpParams(
    val name: String,
    val emailId: String,
    val stdCode: String,
    val mobileNo: Long,
    val msbPin: Int,
    val age: Int,
    val gender: String,
    val relationship: String,
    val address: String,
    val profile: String,
    val companyUniversityCollege: String,
    val interestedIn: String,
    val profession: String,
)
