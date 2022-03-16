package xyz.teknol.network.retrofit

import okhttp3.MultipartBody
import retrofit2.http.*
import xyz.teknol.core.auth.domain.*
import xyz.teknol.core.profile.domain.AnonymousProfileData
import xyz.teknol.core.profile.domain.UpdateAnonymousProfileParams
import xyz.teknol.core.profile.domain.UploadUserProfileData
import xyz.teknol.core.totd.domain.CreateTodData
import xyz.teknol.core.totd.domain.CreateTodParams
import xyz.teknol.core.user.domain.UserListDataItem
import xyz.teknol.core.user.domain.UserListParams
import xyz.teknol.network.adapter.NetworkResponse
import xyz.teknol.network.modals.ErrorModel
import xyz.teknol.network.modals.SignInModel

interface ApiService {

    @POST("users/")
    suspend fun signUp(@Body params: SignUpParams): NetworkResponse<SignUpData, ErrorModel>

    @GET("users/{mobile}/{msbpin}")
    suspend fun signIn(
        @Path(value = "mobile") mobile: String,
        @Path(value = "msbpin") msbpin: String
    ): NetworkResponse<SignInModel, ErrorModel>

    @GET("otp/{mobileNumber}/{stdCode}")
    suspend fun getOtp(
        @Path(value = "stdCode") stdCode: String,
        @Path(value = "mobileNumber") mobile: String
    ): NetworkResponse<GetOtpData, ErrorModel>

    @GET("/otp/{mobileNumber}/{otp}/{stdCode}")
    suspend fun verifyOtp(
        @Path(value = "stdCode") stdCode: String,
        @Path(value = "otp") otp: String,
        @Path(value = "mobileNumber") mobile: String
    ): NetworkResponse<VerifyOtpData, ErrorModel>

    @GET("users/{id}")
    suspend fun getUserProfile(
        @Path(value = "id") id: String
    ): NetworkResponse<GetUserProfileData, ErrorModel>

    @GET("anonymous-users/{id}")
    suspend fun getAnonymousUserProfile(
        @Path(value = "id") id: String
    ): NetworkResponse<AnonymousProfileData, ErrorModel>

    @PUT("anonymous-users/{id}")
    suspend fun updateAnonymousUserProfile(
        @Path(value = "id") id: String,
        @Body params: UpdateAnonymousProfileParams
    ): NetworkResponse<AnonymousProfileData, ErrorModel>


    @GET("/anonymous-users/profile-pics/")
    suspend fun getAnonymousUserProfilePics():
            NetworkResponse<GetAnonymousProfileData, ErrorModel>

    @POST("users/usersByMobileNumbers")
    suspend fun getUserList(@Body params: List<UserListParams>):
            NetworkResponse<List<UserListDataItem>, ErrorModel>

    @PUT("users/{id}")
    suspend fun updateProfile(
        @Path(value = "id") id: String,
        @Body params: SignUpParams
    ): NetworkResponse<SignUpData, ErrorModel>

    @PUT("users/mobileNumber/{mobileNo}/msbPin/{pin}")
    suspend fun changePassword(
        @Path(value = "mobileNo") mobileNo: String,
        @Path(value = "pin") pin: String,
    ): NetworkResponse<ChangePasswordData, ErrorModel>


    @GET("users/fingerprint/{fingerprint}")
    suspend fun getUserByFingerprint(
        @Path(value = "fingerprint") fingerprint: String
    ): NetworkResponse<GetUserByFingerprintData, ErrorModel>

    @POST("users/fingerprint/")
    suspend fun registerFingerprintUser(
        @Body params: RegisterFingerprintUserParams
    ): NetworkResponse<GetUserByFingerprintData, ErrorModel>

    @Multipart
    @POST("/users/profilePic/{realProfileId}")
    suspend fun uploadFile(
        @Path(value = "realProfileId") id: String,
        @Part file: MultipartBody.Part,
    ): NetworkResponse<UploadUserProfileData, ErrorModel>

    @POST("totds/")
    suspend fun createTod(
        @Body params: CreateTodParams
    ): NetworkResponse<CreateTodData, ErrorModel>

}