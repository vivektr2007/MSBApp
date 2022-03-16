package xyz.teknol.core.profile.interactors

import xyz.teknol.core.profile.data.ProfileRepository

class UploadFileUseCase(private val repository: ProfileRepository) {
    suspend operator fun invoke(path: String, id: String) = repository.uploadFile(path, id)
}

