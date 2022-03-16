package xyz.teknol.core.auth.interactors

import xyz.teknol.core.auth.data.AuthRepository
import xyz.teknol.core.auth.domain.GetOtpParams
import xyz.teknol.core.auth.domain.RegisterFingerprintUserParams
import xyz.teknol.core.auth.domain.SignUpParams
import xyz.teknol.core.auth.domain.VerifyOtpParams

class SignUpUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(params: SignUpParams) = repository.signup(params)
    suspend operator fun invoke(params: GetOtpParams) = repository.getOtp(params)
    suspend operator fun invoke(params: VerifyOtpParams) = repository.verifyOtp(params)
    suspend operator fun invoke(params: RegisterFingerprintUserParams) =
        repository.registerFingerprintUser(params)
}