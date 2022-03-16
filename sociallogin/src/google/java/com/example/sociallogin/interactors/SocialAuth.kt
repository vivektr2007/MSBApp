package com.example.sociallogin.interactors

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.MediatorLiveData
import com.example.sociallogin.model.SocialUser
import com.example.sociallogin.providers.GoogleLogin

class SocialAuth {

    private lateinit var googleLogin: GoogleLogin
    val socialUser: MediatorLiveData<SocialUser> = MediatorLiveData()
    val socialError: MediatorLiveData<String> = MediatorLiveData()

    fun loginWithGoogle(activity: Activity): Intent {
        googleLogin = GoogleLogin(activity)
        socialUser.addSource(googleLogin.googleUser) { socialUser.postValue(it) }
        socialError.addSource(googleLogin.error) { socialError.postValue(it) }
        return googleLogin.authorize()
    }

    fun handleGoogleResult(data: Intent?) {
        if (!::googleLogin.isInitialized)
            throw RuntimeException("Call loginWithGoogle first")
        googleLogin.handleResult(data)
    }
}