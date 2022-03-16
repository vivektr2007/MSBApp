package com.msb.framework.di

import com.msb.presentation.authorization.forgotpassword.ChangePasswordViewModel
import com.msb.presentation.authorization.login.LoginViewModel
import com.msb.presentation.authorization.signup.RegisterUserViewModel
import com.msb.presentation.main.chat.ChatListViewModel
import com.msb.presentation.main.dashboard.DashBoradViewModel
import com.msb.presentation.main.userprofile.UserProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {
    viewModel() {
        RegisterUserViewModel(get())
    }
    viewModel() {
        LoginViewModel(get())
    }
    viewModel() {
        UserProfileViewModel(get(), get(), get(), get(), get())
    }
    viewModel() {
        ChangePasswordViewModel(get())
    }
    viewModel() {
        DashBoradViewModel(get())
    }
    viewModel() {
        ChatListViewModel(get())
    }
}