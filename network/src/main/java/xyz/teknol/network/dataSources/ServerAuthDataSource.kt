package xyz.teknol.network.dataSources

import xyz.teknol.core.auth.data.AuthDataSource
import xyz.teknol.core.auth.domain.*
import xyz.teknol.core.domain.Either
import xyz.teknol.core.domain.Failure
import xyz.teknol.database.SharedPreferenceManager
import xyz.teknol.network.adapter.NetworkResponse
import xyz.teknol.network.retrofit.ApiService

class ServerAuthDataSource(
    private val apiService: ApiService,
    private val preferenceManager: SharedPreferenceManager
) : AuthDataSource {

    override suspend fun signup(params: SignUpParams): Either<Failure, SignUpData> {
        return when (val response = apiService.signUp(params)) {
            is NetworkResponse.Success -> {
                return Either.Right(response.body)
            }
            is NetworkResponse.ApiError -> Either.Left(AuthFailure.UnknownError(response.body.error))
            is NetworkResponse.NetworkError -> Either.Left(Failure.NetworkConnection)
            is NetworkResponse.UnknownError -> Either.Left(Failure.ServerError)
        }
    }

    override suspend fun signin(params: SignInParams): Either<Failure, SignInData> {
        return when (val response = apiService.signIn(params.mobileNo, params.msbPin)) {
            is NetworkResponse.Success -> {
                return Either.Right(
                    SignInData(
                        response.body.address,
                        response.body.age,
                        response.body.companyUniversityCollege,
                        response.body.createdAt,
                        response.body.emailId,
                        response.body.fingerprint,
                        response.body.gender,
                        response.body.id,
                        response.body.interestedIn,
                        response.body.mesiboUser?.let {
                            SignInData.MesiboUser(
                                it.address,
                                it.token,
                                it.uid,
                            )
                        },
                        response.body.mobileNo,
                        response.body.msbPin,
                        response.body.name,
                        response.body.profession,
                        response.body.profile,
                        response.body.profilePic,
                        response.body.profilePicFullPath,
                        response.body.relationship,
                        response.body.stdCode,
                        response.body.updatedAt,
                    )
                )
            }
            is NetworkResponse.ApiError -> Either.Left(AuthFailure.UnknownError(response.body.error))
            is NetworkResponse.NetworkError -> Either.Left(Failure.NetworkConnection)
            is NetworkResponse.UnknownError -> Either.Left(Failure.ServerError)
        }
    }


    override suspend fun getUserProfile(params: GetUserProfileParams): Either<Failure, GetUserProfileData> {
        return when (val response = apiService.getUserProfile(params.id)) {
            is NetworkResponse.Success -> {
                return Either.Right(
                    response.body
//                    GetUserProfileData(
//                        response.body.address!!,
//                        response.body.age!!,
//                        response.body.companyUniversityCollege!!,
//                        response.body.createdAt!!,
//                        response.body.emailId!!,
//                        response.body.gender!!,
//                        response.body.id!!,
//                        response.body.interestedIn!!,
//                        response.body.mobileNo!!,
//                        response.body.msbPin!!,
//                        response.body.name!!,
//                        response.body.profile!!,
//                        response.body.relationship!!,
//                        response.body.stdCode!!,
//                        response.body.updatedAt!!,
//                    )
                )
            }
            is NetworkResponse.ApiError -> Either.Left(AuthFailure.UnknownError(response.body.error))
            is NetworkResponse.NetworkError -> Either.Left(Failure.NetworkConnection)
            is NetworkResponse.UnknownError -> Either.Left(Failure.ServerError)
        }
    }

    override suspend fun getOtp(params: GetOtpParams): Either<Failure, GetOtpData> {
        return when (val response = apiService.getOtp(params.stdCode, params.mobileNo)) {
            is NetworkResponse.Success -> {
                return Either.Right(
                    response.body
                )
            }
            is NetworkResponse.ApiError -> Either.Left(AuthFailure.UnknownError(response.body.error))
            is NetworkResponse.NetworkError -> Either.Left(Failure.NetworkConnection)
            is NetworkResponse.UnknownError -> Either.Left(Failure.ServerError)
        }
    }

    override suspend fun verifyOtp(params: VerifyOtpParams): Either<Failure, VerifyOtpData> {

        return when (val response =
            apiService.verifyOtp(params.stdCode, params.mobileNumber, params.otp)) {
            is NetworkResponse.Success -> {
                return Either.Right(
                    response.body
                )
            }
            is NetworkResponse.ApiError -> Either.Left(AuthFailure.UnknownError(response.body.error))
            is NetworkResponse.NetworkError -> Either.Left(Failure.NetworkConnection)
            is NetworkResponse.UnknownError -> Either.Left(Failure.ServerError)
        }
    }

    override suspend fun updateProfile(params: SignUpParams): Either<Failure, SignUpData> {
        return when (val response = apiService.updateProfile(preferenceManager.getId()!!, params)) {
            is NetworkResponse.Success -> {
                return Either.Right(
                    response.body
                )
            }
            is NetworkResponse.ApiError -> Either.Left(AuthFailure.UnknownError(response.body.error))
            is NetworkResponse.NetworkError -> Either.Left(Failure.NetworkConnection)
            is NetworkResponse.UnknownError -> Either.Left(Failure.ServerError)
        }
    }

    override suspend fun getAnonymousUserProfilePics(): Either<Failure, GetAnonymousProfileData> {
        return when (val response = apiService.getAnonymousUserProfilePics()) {
            is NetworkResponse.Success -> {
                return Either.Right(
                    response.body
                )
            }
            is NetworkResponse.ApiError -> Either.Left(AuthFailure.UnknownError(response.body.error))
            is NetworkResponse.NetworkError -> Either.Left(Failure.NetworkConnection)
            is NetworkResponse.UnknownError -> Either.Left(Failure.ServerError)
        }
    }

    override suspend fun changePassword(
        mobileNo: String,
        pin: String
    ): Either<Failure, ChangePasswordData> {
        return when (val response = apiService.changePassword(mobileNo, pin)) {
            is NetworkResponse.Success -> {
                return Either.Right(
                    response.body
                )
            }
            is NetworkResponse.ApiError -> Either.Left(AuthFailure.UnknownError(response.body.error))
            is NetworkResponse.NetworkError -> Either.Left(Failure.NetworkConnection)
            is NetworkResponse.UnknownError -> Either.Left(Failure.ServerError)
        }
    }

    override suspend fun getUserByFingerprint(params: GetUserByFingerprintParams): Either<Failure, GetUserByFingerprintData> {
        return when (val response = apiService.getUserByFingerprint(params.fingerprint)) {
            is NetworkResponse.Success -> {
                return Either.Right(
                    response.body
                )
            }
            is NetworkResponse.ApiError -> Either.Left(AuthFailure.UnknownError(response.body.error))
            is NetworkResponse.NetworkError -> Either.Left(Failure.NetworkConnection)
            is NetworkResponse.UnknownError -> Either.Left(Failure.ServerError)
        }
    }

    override suspend fun registerFingerprintUser(params: RegisterFingerprintUserParams): Either<Failure, GetUserByFingerprintData> {
        return when (val response = apiService.registerFingerprintUser(params)) {
            is NetworkResponse.Success -> {
                return Either.Right(
                    response.body
                )
            }
            is NetworkResponse.ApiError -> Either.Left(AuthFailure.UnknownError(response.body.error))
            is NetworkResponse.NetworkError -> Either.Left(Failure.NetworkConnection)
            is NetworkResponse.UnknownError -> Either.Left(Failure.ServerError)
        }
    }
}