package xyz.teknol.core.auth.interactors

import xyz.teknol.core.auth.data.AuthRepository

class ChangePasswordUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(mobileNo: String, pin: String) = repository.changePassword(
        mobileNo, pin
    )
}