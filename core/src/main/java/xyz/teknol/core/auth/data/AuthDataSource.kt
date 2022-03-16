package xyz.teknol.core.auth.data

import xyz.teknol.core.auth.domain.*
import xyz.teknol.core.domain.Either
import xyz.teknol.core.domain.Failure

interface AuthDataSource {
    suspend fun signup(params: SignUpParams): Either<Failure, SignUpData>
    suspend fun updateProfile(params: SignUpParams): Either<Failure, SignUpData>
    suspend fun signin(params: SignInParams): Either<Failure, SignInData>
    suspend fun getUserProfile(params: GetUserProfileParams): Either<Failure, GetUserProfileData>
    suspend fun getOtp(params: GetOtpParams): Either<Failure, GetOtpData>
    suspend fun verifyOtp(params: VerifyOtpParams): Either<Failure, VerifyOtpData>
    suspend fun getAnonymousUserProfilePics(): Either<Failure, GetAnonymousProfileData>
    suspend fun changePassword(mobileNo: String, pin: String): Either<Failure, ChangePasswordData>
    suspend fun getUserByFingerprint(params: GetUserByFingerprintParams): Either<Failure, GetUserByFingerprintData>
    suspend fun registerFingerprintUser(params: RegisterFingerprintUserParams): Either<Failure, GetUserByFingerprintData>
}
