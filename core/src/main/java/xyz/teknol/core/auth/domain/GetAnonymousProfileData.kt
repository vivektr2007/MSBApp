package xyz.teknol.core.auth.domain

class GetAnonymousProfileData : ArrayList<GetAnonymousProfileData.GetAnonymousProfileDataItem>(){
    data class GetAnonymousProfileDataItem(
        val createdAt: String?,
        val id: String?,
        val profilePic: String?,
        val profilePicFullPath: String?
    )
}