package xyz.teknol.network.dataSources

import xyz.teknol.core.auth.domain.AuthFailure
import xyz.teknol.core.domain.Either
import xyz.teknol.core.domain.Failure
import xyz.teknol.core.totd.data.CreateTodDataSource
import xyz.teknol.core.totd.domain.CreateTodData
import xyz.teknol.core.totd.domain.CreateTodParams
import xyz.teknol.network.adapter.NetworkResponse
import xyz.teknol.network.retrofit.ApiService


class ServerCreateTodDataSource(private val apiService: ApiService) : CreateTodDataSource {
    override suspend fun createTod(params: CreateTodParams): Either<Failure, CreateTodData> {
        return when (val response = apiService.createTod(params)) {
            is NetworkResponse.Success -> Either.Right(response.body)
            is NetworkResponse.ApiError -> Either.Left(AuthFailure.UnknownError(response.body.error))
            is NetworkResponse.NetworkError -> Either.Left(Failure.NetworkConnection)
            is NetworkResponse.UnknownError -> Either.Left(Failure.ServerError)
        }
    }


}