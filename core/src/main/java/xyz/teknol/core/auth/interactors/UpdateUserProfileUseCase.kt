package xyz.teknol.core.auth.interactors

import xyz.teknol.core.auth.data.AuthRepository
import xyz.teknol.core.auth.domain.SignUpParams

class UpdateUserProfileUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(params: SignUpParams) = repository.updateProfile(params)
    suspend operator fun invoke() = repository.getAnonymousUserProfilePics()
}