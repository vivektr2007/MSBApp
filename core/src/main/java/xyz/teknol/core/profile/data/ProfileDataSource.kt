package xyz.teknol.core.profile.data

import xyz.teknol.core.domain.Either
import xyz.teknol.core.domain.Failure
import xyz.teknol.core.profile.domain.AnonymousProfileData
import xyz.teknol.core.profile.domain.UpdateAnonymousProfileParams
import xyz.teknol.core.profile.domain.UploadUserProfileData

interface ProfileDataSource {
    suspend fun getAnonymousUserProfile(params: String): Either<Failure, AnonymousProfileData>
    suspend fun updateAnonymousUserProfile(
        userId: String,
        params: UpdateAnonymousProfileParams
    ): Either<Failure, AnonymousProfileData>

    suspend fun uploadFile(path: String,id: String): Either<Failure, UploadUserProfileData>
}