package xyz.teknol.network.dataSources

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import xyz.teknol.core.auth.domain.AuthFailure
import xyz.teknol.core.domain.Either
import xyz.teknol.core.domain.Failure
import xyz.teknol.core.profile.data.ProfileDataSource
import xyz.teknol.core.profile.domain.AnonymousProfileData
import xyz.teknol.core.profile.domain.UpdateAnonymousProfileParams
import xyz.teknol.core.profile.domain.UploadUserProfileData
import xyz.teknol.network.adapter.NetworkResponse
import xyz.teknol.network.retrofit.ApiService
import java.io.File

class ServerProfileDataSource(private val apiService: ApiService) : ProfileDataSource {
    override suspend fun getAnonymousUserProfile(params: String): Either<Failure, AnonymousProfileData> {
        return when (val response = apiService.getAnonymousUserProfile(params)) {
            is NetworkResponse.Success -> Either.Right(response.body)
            is NetworkResponse.ApiError -> Either.Left(AuthFailure.UnknownError(response.body.error))
            is NetworkResponse.NetworkError -> Either.Left(Failure.NetworkConnection)
            is NetworkResponse.UnknownError -> Either.Left(Failure.ServerError)
        }
    }

    override suspend fun updateAnonymousUserProfile(
        userId: String,
        params: UpdateAnonymousProfileParams
    ): Either<Failure, AnonymousProfileData> {
        return when (val response = apiService.updateAnonymousUserProfile(userId, params)) {
            is NetworkResponse.Success -> Either.Right(response.body)
            is NetworkResponse.ApiError -> Either.Left(AuthFailure.UnknownError(response.body.error))
            is NetworkResponse.NetworkError -> Either.Left(Failure.NetworkConnection)
            is NetworkResponse.UnknownError -> Either.Left(Failure.ServerError)
        }
    }

    override suspend fun uploadFile(
        path: String,
        id: String
    ): Either<Failure, UploadUserProfileData> {
        val file = File(path)
        val requestFile: RequestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
        val body: MultipartBody.Part =
            MultipartBody.Part.createFormData("file", file.name, requestFile)
        return when (val response = apiService.uploadFile(id, body)) {
            is NetworkResponse.Success -> Either.Right(response.body)
            is NetworkResponse.ApiError -> Either.Left(AuthFailure.UnknownError(response.body.error))
            is NetworkResponse.NetworkError -> Either.Left(Failure.NetworkConnection)
            is NetworkResponse.UnknownError -> Either.Left(Failure.ServerError)
        }
    }

}