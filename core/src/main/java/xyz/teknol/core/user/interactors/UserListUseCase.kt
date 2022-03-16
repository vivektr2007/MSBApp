package xyz.teknol.core.user.interactors

import xyz.teknol.core.user.data.UserRepository
import xyz.teknol.core.user.domain.UserListParams

class UserListUseCase(private val repository: UserRepository) {
    suspend operator fun invoke(params: List<UserListParams>) = repository.getUserList(params)
}

