package com.msb.presentation.authorization.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import xyz.teknol.core.auth.domain.GetUserByFingerprintData
import xyz.teknol.core.auth.domain.GetUserByFingerprintParams
import xyz.teknol.core.auth.domain.SignInData
import xyz.teknol.core.auth.domain.SignInParams
import xyz.teknol.core.auth.interactors.SignInUseCase
import xyz.teknol.core.domain.Failure

class LoginViewModel(private val signInUseCase: SignInUseCase) : ViewModel() {

    private val _failure: MutableLiveData<Failure> = MutableLiveData()
    val failure: LiveData<Failure> = _failure

    private val _loginUser: MutableLiveData<SignInData> = MutableLiveData()
    val loginUser: LiveData<SignInData> = _loginUser

    private val _userByFingerprintData: MutableLiveData<GetUserByFingerprintData> =MutableLiveData()
    val userByFingerprintData: LiveData<GetUserByFingerprintData> = _userByFingerprintData

    private val _loader: MutableLiveData<Boolean> = MutableLiveData()
    val loader: LiveData<Boolean> = _loader

    fun login(mobile: String, msbpin: String) {
        _loader.value = true
        viewModelScope.launch {
            signInUseCase(SignInParams(mobile, msbpin)).fold(::handleFailure, ::handleLogin)
        }
    }

    fun getUserByFingerprint(fingerprint: String) {
        _loader.value = true
        viewModelScope.launch {
            signInUseCase(GetUserByFingerprintParams(fingerprint)).fold(
                ::handleFailure,
                ::handleFingerprintLogin
            )
        }
    }

    private fun handleLogin(loginUser: SignInData) {
        _loader.value = false
        _loginUser.value = loginUser
    }

    private fun handleFingerprintLogin(getUserByFingerprintData: GetUserByFingerprintData) {
        _loader.value = false
        _userByFingerprintData.value = getUserByFingerprintData
    }

    private fun handleFailure(failure: Failure) {
        _loader.value = false
        _failure.value = failure
    }

}