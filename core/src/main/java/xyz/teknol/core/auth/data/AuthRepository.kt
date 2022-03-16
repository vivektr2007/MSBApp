package xyz.teknol.core.auth.data

import xyz.teknol.core.auth.domain.*
import xyz.teknol.core.domain.Either
import xyz.teknol.core.domain.Failure

class AuthRepository(private val dataSource: AuthDataSource) {
    suspend fun signup(params: SignUpParams): Either<Failure, SignUpData> =
        dataSource.signup(params)

    suspend fun updateProfile(params: SignUpParams): Either<Failure, SignUpData> =
        dataSource.updateProfile(params)

    suspend fun signin(params: SignInParams): Either<Failure, SignInData> =
        dataSource.signin(params)

    suspend fun getUserProfile(params: GetUserProfileParams): Either<Failure, GetUserProfileData> =
        dataSource.getUserProfile(params)

    suspend fun getUserByFingerprint(params: GetUserByFingerprintParams): Either<Failure, GetUserByFingerprintData> =
        dataSource.getUserByFingerprint(params)

    suspend fun registerFingerprintUser(params: RegisterFingerprintUserParams): Either<Failure, GetUserByFingerprintData> =
        dataSource.registerFingerprintUser(params)

    suspend fun getOtp(params: GetOtpParams): Either<Failure, GetOtpData> =
        dataSource.getOtp(params)

    suspend fun verifyOtp(params: VerifyOtpParams): Either<Failure, VerifyOtpData> =
        dataSource.verifyOtp(params)

    suspend fun getAnonymousUserProfilePics(): Either<Failure, GetAnonymousProfileData> =
        dataSource.getAnonymousUserProfilePics()

    suspend fun changePassword(mobileNo: String, pin: String): Either<Failure, ChangePasswordData> =
        dataSource.changePassword(mobileNo, pin)
}