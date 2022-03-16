package com.msb.presentation.main.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import xyz.teknol.core.domain.Failure
import xyz.teknol.core.user.domain.UserListDataItem
import xyz.teknol.core.user.domain.UserListParams
import xyz.teknol.core.user.interactors.UserListUseCase

class ChatListViewModel(private val userListUseCase: UserListUseCase) : ViewModel() {
    private val _failure: MutableLiveData<Failure> = MutableLiveData()
    val failure: LiveData<Failure> = _failure

    private val _userList: MutableLiveData<List<UserListDataItem>> = MutableLiveData()
    val userList: LiveData<List<UserListDataItem>> = _userList

    private val _loader: MutableLiveData<Boolean> = MutableLiveData()
    val loader: LiveData<Boolean> = _loader

    fun getUserList(params: ArrayList<UserListParams>) {
        _loader.value = true
        viewModelScope.launch {
            userListUseCase(params).fold(::handleFailure, ::handleSuccess)
        }
    }

    private fun handleSuccess(list: List<UserListDataItem>) {
        _loader.value = false
        _userList.value = list
    }

    private fun handleFailure(failure: Failure) {
        _loader.value = false
        _failure.value = failure
    }
}