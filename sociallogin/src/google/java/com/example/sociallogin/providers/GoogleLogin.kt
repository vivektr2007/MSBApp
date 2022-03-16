package com.example.sociallogin.providers

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.MutableLiveData
import com.example.googleauth.GoogleAuthenticator
import com.example.googleauth.contract.GoogleContract
import com.example.googleauth.model.GoogleUser
import com.example.sociallogin.R
import com.example.sociallogin.model.SocialType
import com.example.sociallogin.model.SocialUser

class GoogleLogin(private val activity: Activity) : GoogleContract {

    val googleUser: MutableLiveData<SocialUser> = MutableLiveData()
    val error: MutableLiveData<String> = MutableLiveData()

    private val googleAuthenticator =
        GoogleAuthenticator(this, activity.getString(R.string.google_web_client_id))


    override fun authorize(): Intent =
        googleAuthenticator.login(activity)

    override fun handleResult(data: Intent?) {
        googleAuthenticator.handleActivityResult(data)
    }

    override fun onAuthorizationSuccess(userDetails: GoogleUser) {
        googleUser.postValue(
            SocialUser(
                userDetails.id,
                if (userDetails.accessToken != null) userDetails.accessToken!! else "",
                userDetails.email,
                userDetails.name,
                userDetails.image,
                SocialType.GOOGLE
            )
        )
    }

    override fun onAuthorizationFailed(errorDetails: String) {
        error.postValue(errorDetails)
    }
}