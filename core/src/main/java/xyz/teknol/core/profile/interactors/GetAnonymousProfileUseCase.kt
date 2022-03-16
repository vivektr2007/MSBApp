package xyz.teknol.core.profile.interactors

import xyz.teknol.core.profile.data.ProfileRepository

class GetAnonymousProfileUseCase(private val repository: ProfileRepository) {
    suspend operator fun invoke(userId: String) = repository.getAnonymousUserProfile(userId)
}