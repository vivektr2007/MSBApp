package com.msb.presentation.authorization.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import xyz.teknol.core.auth.domain.*
import xyz.teknol.core.auth.interactors.SignUpUseCase
import xyz.teknol.core.domain.Failure

class RegisterUserViewModel(private val signUpUseCase: SignUpUseCase) : ViewModel() {

    private val _failure: MutableLiveData<Failure> = MutableLiveData()
    val failure: LiveData<Failure> = _failure

    private val _loader: MutableLiveData<Boolean> = MutableLiveData()
    val loader: LiveData<Boolean> = _loader

    private val _signUpData: MutableLiveData<SignUpData> = MutableLiveData()
    val signUpData: LiveData<SignUpData> = _signUpData

    private val _otpData: MutableLiveData<GetOtpData> = MutableLiveData()
    val otpData: LiveData<GetOtpData> = _otpData
    private val _userByFingerprintData: MutableLiveData<GetUserByFingerprintData> =
        MutableLiveData()
    val userByFingerprintData: LiveData<GetUserByFingerprintData> = _userByFingerprintData
    private val _verifyOtpData: MutableLiveData<VerifyOtpData> = MutableLiveData()
    val verifyOtpData: LiveData<VerifyOtpData> = _verifyOtpData

    fun signUp(name: String, email: String, phone: String, msbpin: String, stdCode: String) {
        _loader.value = true
        viewModelScope.launch {
            signUpUseCase(
                SignUpParams(
                    name, email, stdCode, phone.toLong(), msbpin.toInt(), 0, "",
                    "", "", "", "", "", ""
                )
            ).fold(
                ::handleFailure,
                ::handleSuccess
            )
        }
    }

    fun getOtp(stdCode: String, Phone: String) {
        _loader.value = true
        viewModelScope.launch {
            signUpUseCase(
                GetOtpParams(

                    Phone,
                    stdCode
                )
            ).fold(
                ::handleFailure,
                ::handleOtpSuccess
            )
        }
    }

    fun verifyOtp(selectedCountryCode: String, phone: String, otp: String) {
        _loader.value = true
        viewModelScope.launch {
            signUpUseCase(
                VerifyOtpParams(

                    phone, otp, selectedCountryCode,
                )
            ).fold(
                ::handleFailure,
                ::handleOtpVerifySuccess
            )
        }
    }

    fun registerFingureprint(
        fingureprint: String,
        mobile: String, msbpin: String
    ) {
        _loader.value = true
        viewModelScope.launch {
            signUpUseCase(
                RegisterFingerprintUserParams(
                    fingureprint,
                    mobile.toLong(),
                    msbpin.toInt()
                )
            ).fold(::handleFailure, ::handleFingerprintLogin)
        }
    }

    private fun handleSuccess(isSuccess: SignUpData) {
        _loader.value = false
        _signUpData.value = isSuccess
    }

    private fun handleOtpVerifySuccess(verifyOtpData: VerifyOtpData) {
        _loader.value = false
        _verifyOtpData.value = verifyOtpData
    }

    private fun handleOtpSuccess(isSuccess: GetOtpData) {
        _loader.value = false
        _otpData.value = isSuccess
    }

    private fun handleFailure(failure: Failure) {
        _failure.value = failure
        _loader.value = false
    }

    private fun handleFingerprintLogin(getUserByFingerprintData: GetUserByFingerprintData) {
        _loader.value = false
        _userByFingerprintData.value = getUserByFingerprintData
    }
}