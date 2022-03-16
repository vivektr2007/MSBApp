package com.msb.presentation.main.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import xyz.teknol.core.chat.domain.SendMessageData
import xyz.teknol.core.chat.interactors.SendMessageUseCase
import xyz.teknol.core.domain.Failure
import xyz.teknol.core.user.domain.UserListDataItem
import xyz.teknol.core.user.domain.UserListParams
import xyz.teknol.core.user.interactors.UserListUseCase

class SendMessageViewModel(private val sendMessageUseCase: SendMessageUseCase) : ViewModel() {
    private val _failure: MutableLiveData<Failure> = MutableLiveData()
    val failure: LiveData<Failure> = _failure

    private val _sendDataList: MutableLiveData<List<SendMessageData>> = MutableLiveData()
    val sendDataList: LiveData<List<SendMessageData>> = _sendDataList

    private val _loader: MutableLiveData<Boolean> = MutableLiveData()
    val loader: LiveData<Boolean> = _loader

//    fun sendMessage() {
//        _loader.value = true
//        viewModelScope.launch {
//            sendMessageUseCase(params).fold(::handleFailure, ::handleSuccess)
//        }
//    }

    private fun handleSuccess(list: List<SendMessageData>) {
        _loader.value = false
        _sendDataList.value = list
    }

    private fun handleFailure(failure: Failure) {
        _loader.value = false
        _failure.value = failure
    }
}