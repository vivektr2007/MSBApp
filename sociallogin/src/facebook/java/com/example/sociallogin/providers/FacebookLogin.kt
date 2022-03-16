//package com.example.sociallogin.providers
//
//import android.app.Activity
//import android.content.Intent
//import androidx.fragment.app.Fragment
//import androidx.lifecycle.MutableLiveData
//import com.example.facebookauth.FacebookAuthenticator
//import com.example.facebookauth.contract.FacebookContract
//import com.example.facebookauth.model.FacebookUser
//import com.example.googleauth.model.GoogleUser
//import com.example.sociallogin.model.SocialType
//import com.example.sociallogin.model.SocialUser
//
//class FacebookLogin : FacebookContract {
//
//    val fbUser: MutableLiveData<SocialUser> = MutableLiveData()
//    val error: MutableLiveData<String> = MutableLiveData()
//
//    private val facebookAuthenticator = FacebookAuthenticator(this)
//
//    override fun authorize(activity: Activity) {
//        facebookAuthenticator.loginWithFacebook(activity)
//    }
//
//    override fun authorize(fragment: Fragment) {
//        facebookAuthenticator.loginWithFacebook(fragment)
//    }
//
//
//    override fun handleResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        facebookAuthenticator.handleActivityResult(requestCode, resultCode, data)
//    }
//
//    override fun onAuthorizationSuccess(userDetails: FacebookUser) {
//        fbUser.postValue(
//            SocialUser(
//                userDetails.id,
//                userDetails.accessToken,
//                userDetails.email,
//                userDetails.name,
//                userDetails.image,
//                SocialType.GOOGLE
//            )
//        )
//    }
//
//    override fun onAuthorizationFailed(errorDetails: String) {
//        error.postValue(errorDetails)
//    }
//}