package com.msb.presentation.authorization.forgotpassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import xyz.teknol.core.auth.domain.ChangePasswordData
import xyz.teknol.core.auth.interactors.ChangePasswordUseCase
import xyz.teknol.core.domain.Failure

class ChangePasswordViewModel(private val changePasswordUseCase: ChangePasswordUseCase) :
    ViewModel() {
    private val _failure: MutableLiveData<Failure> = MutableLiveData()
    val failure: LiveData<Failure> = _failure

    private val _loader: MutableLiveData<Boolean> = MutableLiveData()
    val loader: LiveData<Boolean> = _loader

    private val _success: MutableLiveData<Boolean> = MutableLiveData()
    val success: LiveData<Boolean> = _success

    fun changePassword(pin: String, mobileNo: String) {
        _loader.value = true
        viewModelScope.launch {
            changePasswordUseCase(
                mobileNo, pin
            ).fold(
                ::handleFailure,
                ::handleSuccess
            )
        }
    }

    private fun handleSuccess(changePasswordData: ChangePasswordData) {
        _loader.value = false
        _success.value = true
    }

    private fun handleFailure(failure: Failure) {
        _failure.value = failure
        _loader.value = false
    }
}