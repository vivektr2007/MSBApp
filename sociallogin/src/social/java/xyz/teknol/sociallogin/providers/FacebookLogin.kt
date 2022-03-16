package xyz.teknol.sociallogin.providers

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import xyz.teknol.facebookauth.FacebookAuthenticator
import xyz.teknol.facebookauth.contract.FacebookContract
import xyz.teknol.facebookauth.model.FacebookUser
import xyz.teknol.sociallogin.model.SocialType
import xyz.teknol.sociallogin.model.SocialUser

class FacebookLogin : FacebookContract {

    val fbUser: MutableLiveData<SocialUser> = MutableLiveData()
    val error: MutableLiveData<String> = MutableLiveData()

    private val facebookAuthenticator = FacebookAuthenticator(this)

    override fun authorize(activity: Activity) {
        facebookAuthenticator.loginWithFacebook(activity)
    }

    override fun authorize(fragment: Fragment) {
        facebookAuthenticator.loginWithFacebook(fragment)
    }


    override fun handleResult(requestCode: Int, resultCode: Int, data: Intent?) {
        facebookAuthenticator.handleActivityResult(requestCode, resultCode, data)
    }

    override fun onAuthorizationSuccess(userDetails: FacebookUser) {
        fbUser.postValue(
            SocialUser(
                userDetails.id,
                userDetails.accessToken,
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