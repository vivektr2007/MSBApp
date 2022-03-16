//package com.example.sociallogin.interactors
//
//import android.app.Activity
//import android.content.Intent
//import androidx.fragment.app.Fragment
//import androidx.lifecycle.MediatorLiveData
//import com.example.sociallogin.model.SocialUser
//import com.example.sociallogin.providers.FacebookLogin
//
//class SocialAuth {
//
//    private lateinit var fbLogin: FacebookLogin
//    val socialUser: MediatorLiveData<SocialUser> = MediatorLiveData()
//    val socialError: MediatorLiveData<String> = MediatorLiveData()
//
//    fun loginWithFacebook(activity: Activity) {
//        fbLogin = FacebookLogin()
//        socialUser.addSource(fbLogin.fbUser) { socialUser.postValue(it) }
//        socialError.addSource(fbLogin.error) { socialError.postValue(it) }
//        fbLogin.authorize(activity)
//    }
//
//    fun loginWithFacebook(fragment: Fragment) {
//        fbLogin = FacebookLogin()
//        socialUser.addSource(fbLogin.fbUser) { socialUser.postValue(it) }
//        socialError.addSource(fbLogin.error) { socialError.postValue(it) }
//        fbLogin.authorize(fragment)
//    }
//    fun handleFacebookResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        if (!::fbLogin.isInitialized)
//            throw RuntimeException("Call loginWithFacebook first")
//        fbLogin.handleResult(requestCode, resultCode, data)
//    }
//}