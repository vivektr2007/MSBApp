package com.msb.presentation.main.dashboard

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
import xyz.teknol.core.totd.domain.CreateTodData
import xyz.teknol.core.totd.domain.CreateTodParams
import xyz.teknol.core.totd.interactors.CreateTodUseCase

class DashBoradViewModel(private val createTodUseCase: CreateTodUseCase) : ViewModel() {

    private val _failure: MutableLiveData<Failure> = MutableLiveData()
    val failure: LiveData<Failure> = _failure

    private val _createTodData: MutableLiveData<CreateTodData> = MutableLiveData()
    val createTodData: LiveData<CreateTodData> = _createTodData

    private val _loader: MutableLiveData<Boolean> = MutableLiveData()
    val loader: LiveData<Boolean> = _loader

    fun createTod(userId: String?, string: String) {
        _loader.value = true
        viewModelScope.launch {
            createTodUseCase(CreateTodParams(string, userId, "")).fold(
                ::handleFailure,
                ::handleCreateTodSuccess
            )
        }
    }

    private fun handleCreateTodSuccess(createTodData: CreateTodData) {
        _loader.value = false
        _createTodData.value = createTodData
    }


    private fun handleFailure(failure: Failure) {
        _loader.value = false
        _failure.value = failure
    }

}