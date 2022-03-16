package xyz.teknol.facebookauth

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import xyz.teknol.facebookauth.contract.FacebookContract
import xyz.teknol.facebookauth.model.FacebookUser
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.GraphRequest
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult

class FacebookAuthenticator(private val contract: FacebookContract) {
    private var callbackManager: CallbackManager = CallbackManager.Factory.create()

    init {
        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult?> {
                override fun onSuccess(loginResult: LoginResult?) {
                    getUserDetails(loginResult!!)
                }

                override fun onCancel() {
                    contract.onAuthorizationFailed("Facebook Login Cancelled by User")
                }

                override fun onError(exception: FacebookException) {
                    contract.onAuthorizationFailed("Facebook Error : ${exception.message}")
                }
            })

    }

    private fun getUserDetails(loginResult: LoginResult) {
        val request = GraphRequest.newMeRequest(
            loginResult.accessToken
        ) { _, response ->
            contract.onAuthorizationSuccess(
                FacebookUser(
                    response.jsonObject.getString("id"),
                    loginResult.accessToken.token,
                    response.jsonObject.getString("email"),
                    response.jsonObject.getString("name"),
                    response.jsonObject.getJSONObject("picture").getJSONObject("data")
                        .getString("url")
                )
            )
        }
        val parameters = Bundle()
        parameters.putString("fields", "id,name,email,link,picture.type(large)")
        request.parameters = parameters
        request.executeAsync()
    }

    fun loginWithFacebook(activity: Activity) {
//        if (activity.getString(R.string.facebook_app_id).length < 10)
//            throw RuntimeException("Invalid Facebook Id")
        LoginManager.getInstance()
            .logInWithReadPermissions(activity, listOf("public_profile", "email"))
    }

    fun loginWithFacebook(fragment: Fragment) {
//        if (fragment.getString(R.string.facebook_app_id).length < 10)
//            throw RuntimeException("Invalid Facebook Id")
        LoginManager.getInstance()
            .logInWithReadPermissions(fragment, listOf("public_profile", "email"))
    }

    fun handleActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }
}