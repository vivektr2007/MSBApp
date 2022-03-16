package xyz.teknol.core.user.domain

data class UserListDataItem(
    val address: String?,
    val age: Int?,
    val companyUniversityCollege: String?,
    val createdAt: String?,
    val emailId: String?,
    val fingerprint: String?,
    val gender: String?,
    val id: String?,
    val interestedIn: String?,
    val mesiboUser: MesiboUser?,
    val mobileNo: Long?,
    val msbPin: Int?,
    val name: String?,
    val profession: String?,
    val profile: String?,
    val profilePic: String?,
    val profilePicFullPath: String?,
    val relationship: String?,
    val stdCode: String?,
    val updatedAt: String?
) {
    data class MesiboUser(
        val address: String?,
        val token: String?,
        val uid: String?
    )
}