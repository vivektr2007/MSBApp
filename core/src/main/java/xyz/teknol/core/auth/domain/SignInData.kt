package xyz.teknol.core.auth.domain

data class SignInData(
    val address: String?,
    val age: Int?,
    val companyUniversityCollege: String?,
    val createdAt: String?,
    val emailId: String?,
    val fingerprint: Any?,
    val gender: String?,
    val id: String?,
    val interestedIn: String?,
    val mesiboUser: MesiboUser?,
    val mobileNo: Long?,
    val msbPin: Int?,
    val name: String?,
    val profession: String?,
    val profile: String?,
    val profilePic: Any?,
    val profilePicFullPath: Any?,
    val relationship: String?,
    val stdCode: String?,
    val updatedAt: Any?
) {
    data class MesiboUser(
        val address: String?,
        val token: String?,
        val uid: String?
    )
}