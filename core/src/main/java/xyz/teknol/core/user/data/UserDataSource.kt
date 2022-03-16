package xyz.teknol.core.user.data

import xyz.teknol.core.domain.Either
import xyz.teknol.core.domain.Failure
import xyz.teknol.core.user.domain.UserListDataItem
import xyz.teknol.core.user.domain.UserListParams

interface UserDataSource {
    suspend fun getUserList(params: List<UserListParams>): Either<Failure, List<UserListDataItem>>
}