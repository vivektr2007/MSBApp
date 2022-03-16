package xyz.teknol.core.auth.interactors

import xyz.teknol.core.auth.data.AuthRepository
import xyz.teknol.core.auth.domain.GetUserByFingerprintParams
import xyz.teknol.core.auth.domain.SignInParams

class SignInUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(params: SignInParams) = repository.signin(params)
    suspend operator fun invoke(params: GetUserByFingerprintParams) =
        repository.getUserByFingerprint(params)
}