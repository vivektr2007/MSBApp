package xyz.teknol.core.auth.interactors

import xyz.teknol.core.auth.data.AuthRepository
import xyz.teknol.core.auth.domain.GetUserProfileParams

class GetUserProfileUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(params: GetUserProfileParams) = repository.getUserProfile(params)
}