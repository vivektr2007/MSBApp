package xyz.teknol.core.totd.data

import xyz.teknol.core.domain.Either
import xyz.teknol.core.domain.Failure
import xyz.teknol.core.profile.domain.AnonymousProfileData
import xyz.teknol.core.profile.domain.UpdateAnonymousProfileParams
import xyz.teknol.core.profile.domain.UploadUserProfileData
import xyz.teknol.core.totd.domain.CreateTodData
import xyz.teknol.core.totd.domain.CreateTodParams

class CreateTodRepository(private val dataSource: CreateTodDataSource) {
    suspend fun createTod(params: CreateTodParams): Either<Failure, CreateTodData> =
        dataSource.createTod(params)

}