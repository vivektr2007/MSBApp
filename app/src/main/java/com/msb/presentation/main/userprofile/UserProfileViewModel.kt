package com.msb.presentation.main.userprofile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import xyz.teknol.core.auth.domain.*
import xyz.teknol.core.auth.interactors.GetUserProfileUseCase
import xyz.teknol.core.auth.interactors.UpdateUserProfileUseCase
import xyz.teknol.core.domain.Failure
import xyz.teknol.core.profile.domain.AnonymousProfileData
import xyz.teknol.core.profile.domain.UpdateAnonymousProfileParams
import xyz.teknol.core.profile.domain.UploadUserProfileData
import xyz.teknol.core.profile.interactors.GetAnonymousProfileUseCase
import xyz.teknol.core.profile.interactors.UpdateAnonymousProfileUseCase
import xyz.teknol.core.profile.interactors.UploadFileUseCase

class UserProfileViewModel(
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val updateUserProfileUseCase: UpdateUserProfileUseCase,
    private val getAnonymousProfileUseCase: GetAnonymousProfileUseCase,
    private val uploadFileUseCase: UploadFileUseCase,
    private val updateAnonymousProfileUseCase: UpdateAnonymousProfileUseCase
) : ViewModel() {

    private val _failure: MutableLiveData<Failure> = MutableLiveData()
    val failure: LiveData<Failure> = _failure

    private val _userProfile: MutableLiveData<GetUserProfileData> = MutableLiveData()
    val userProfile: LiveData<GetUserProfileData> = _userProfile

    private val _anonymousProfile: MutableLiveData<AnonymousProfileData> = MutableLiveData()
    val anonymousProfile: LiveData<AnonymousProfileData> = _anonymousProfile

    private val _loader: MutableLiveData<Boolean> = MutableLiveData()
    val loader: LiveData<Boolean> = _loader

    private val _uploadUserProfileData: MutableLiveData<UploadUserProfileData> = MutableLiveData()
    val uploadUserProfileData: LiveData<UploadUserProfileData> = _uploadUserProfileData

    private val _updateUserProfile: MutableLiveData<SignUpData> = MutableLiveData()
    val updateUserProfile: LiveData<SignUpData> = _updateUserProfile

    private val _anonymousUserProfilePicsList: MutableLiveData<GetAnonymousProfileData> =
        MutableLiveData()
    val anonymousUserProfilePicsList: LiveData<GetAnonymousProfileData> =
        _anonymousUserProfilePicsList


    fun updateUserProfile(
        firstName: String,
        lastName: String,
        age: Int,
        genderValue: String,
        relation: String,
        address: String,
        profession: String,
        profilePost: String,
        company: String,
        profilePic: String,
        interestedInValue: String,
        mobile: Long,
        msbpin: Int,
        email: String
    ) {
        _loader.value = true
        viewModelScope.launch {
            updateUserProfileUseCase(
                SignUpParams(
                    "$firstName $lastName",
                    email,
                    "91",
                    mobile,
                    msbpin,
                    age,
                    genderValue,
                    relation,
                    address,
                    profilePic,
                    company,
                    interestedInValue,
                    profession
                    )
            ).fold(::handleFailure, ::handleUpdateProfile)
        }
    }

    fun getUserProfile(id: String) {
        _loader.value = true
        viewModelScope.launch {
            getUserProfileUseCase(GetUserProfileParams(id)).fold(
                ::handleFailure,
                ::handleGetProfile
            )
        }
    }

    fun getAnonymousProfile(id: String) {
        _loader.value = true
        viewModelScope.launch {
            getAnonymousProfileUseCase(id).fold(
                ::handleFailure,
                ::handleAnonymousProfile
            )
        }
    }

    fun uploadFile(path: String, id: String) {
        _loader.value = true
        viewModelScope.launch {
            uploadFileUseCase(path, id).fold(
                ::handleFailure,
                ::handleUserProfileProfile
            )
        }
    }

    fun updateAnonymousProfile(
        id: String?,
        anonymousUserId: String?,
        anonymousUserNearby: Boolean?,
        profilePic: String?,
        revealGender: Boolean?,
        smsCountPerUser: String?
    ) {
        _loader.value = true
        viewModelScope.launch {
            id?.let {
                updateAnonymousProfileUseCase(
                    it, UpdateAnonymousProfileParams(
                        anonymousUserId,
                        anonymousUserNearby,
                        id,
                        profilePic,
                        revealGender,
                        smsCountPerUser
                    )
                ).fold(
                    ::handleFailure,
                    ::handleAnonymousProfile
                )
            }
        }
    }

    private fun handleAnonymousProfile(anonymousProfileData: AnonymousProfileData) {
        _loader.value = false
        _anonymousProfile.value = anonymousProfileData
    }

    private fun handleUserProfileProfile(uploadUserProfileData: UploadUserProfileData) {
        _loader.value = false
        _uploadUserProfileData.value = uploadUserProfileData
    }

    fun getAnonymousUserProfilePics() {
        _loader.value = true
        viewModelScope.launch {
            updateUserProfileUseCase().fold(
                ::handleFailure,
                ::anonymousProfileData
            )
        }
    }

    private fun handleUpdateProfile(signUpData: SignUpData) {
        _loader.value = false
        _updateUserProfile.value = signUpData
    }

    private fun anonymousProfileData(anonymousProfileData: GetAnonymousProfileData) {
        _loader.value = false
        _anonymousUserProfilePicsList.value = anonymousProfileData
    }

    private fun handleGetProfile(loginUser: GetUserProfileData) {
        _loader.value = false
        _userProfile.value = loginUser
    }

    private fun handleFailure(failure: Failure) {
        _loader.value = false
        _failure.value = failure
    }

}