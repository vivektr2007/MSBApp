package xyz.teknol.sociallogin.interactors

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.lifecycle.MediatorLiveData
import xyz.teknol.sociallogin.providers.FacebookLogin
import xyz.teknol.sociallogin.providers.GoogleLogin
import xyz.teknol.sociallogin.model.SocialUser

class SocialAuth {

    private lateinit var fbLogin: FacebookLogin
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

    fun loginWithFacebook(activity: Activity) {
        fbLogin = FacebookLogin()
        socialUser.addSource(fbLogin.fbUser) { socialUser.postValue(it) }
        socialError.addSource(fbLogin.error) { socialError.postValue(it) }
        fbLogin.authorize(activity)
    }

    fun loginWithFacebook(fragment: Fragment) {
        fbLogin = FacebookLogin()
        socialUser.addSource(fbLogin.fbUser) { socialUser.postValue(it) }
        socialError.addSource(fbLogin.error) { socialError.postValue(it) }
        fbLogin.authorize(fragment)
    }

    fun handleFacebookResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (!::fbLogin.isInitialized)
            throw RuntimeException("Call loginWithFacebook first")
        fbLogin.handleResult(requestCode, resultCode, data)
    }
}