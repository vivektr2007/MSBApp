package xyz.teknol.network.dataSources

import xyz.teknol.core.auth.domain.AuthFailure
import xyz.teknol.core.domain.Either
import xyz.teknol.core.domain.Failure
import xyz.teknol.core.user.data.UserDataSource
import xyz.teknol.core.user.domain.UserListDataItem
import xyz.teknol.core.user.domain.UserListParams
import xyz.teknol.network.adapter.NetworkResponse
import xyz.teknol.network.retrofit.ApiService

class ServerUserDataSource(private val apiService: ApiService) : UserDataSource {

    override suspend fun getUserList(params: List<UserListParams>): Either<Failure, List<UserListDataItem>> {
        return when (val response = apiService.getUserList(params)) {
            is NetworkResponse.Success -> Either.Right(response.body)
            is NetworkResponse.ApiError -> Either.Left(AuthFailure.UnknownError(response.body.error))
            is NetworkResponse.NetworkError -> Either.Left(Failure.NetworkConnection)
            is NetworkResponse.UnknownError -> Either.Left(Failure.ServerError)
        }
    }


}