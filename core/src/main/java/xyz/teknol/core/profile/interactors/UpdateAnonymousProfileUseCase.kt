package xyz.teknol.core.profile.interactors

import xyz.teknol.core.profile.data.ProfileRepository
import xyz.teknol.core.profile.domain.UpdateAnonymousProfileParams

class UpdateAnonymousProfileUseCase(private val repository: ProfileRepository) {
    suspend operator fun invoke(userId: String, params: UpdateAnonymousProfileParams) =
        repository.updateAnonymousUserProfile(userId, params)
}