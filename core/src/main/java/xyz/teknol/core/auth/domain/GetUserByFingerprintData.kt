package xyz.teknol.core.auth.domain

data class GetUserByFingerprintData(
    val address: String?,
    val age: Int?,
    val companyUniversityCollege: String?,
    val createdAt: String?,
    val emailId: String?,
    val fingerprint: String?,
    val gender: String?,
    val id: String?,
    val interestedIn: String?,
    val mobileNo: Long?,
    val msbPin: Int?,
    val name: String?,
    val profession: String?,
    val profile: String?,
    val profilePic: String?,
    val profilePicFullPath: Any?,
    val relationship: String?,
    val stdCode: String?,
    val updatedAt: String?
)