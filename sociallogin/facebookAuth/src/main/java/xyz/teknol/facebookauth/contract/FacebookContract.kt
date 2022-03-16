package xyz.teknol.facebookauth.contract

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.Fragment
import xyz.teknol.facebookauth.model.FacebookUser

interface FacebookContract {

    fun authorize(activity: Activity)

    fun authorize(fragment: Fragment)

    fun handleResult(requestCode: Int, resultCode: Int, data: Intent?)

    fun onAuthorizationSuccess(userDetails: FacebookUser)

    fun onAuthorizationFailed(errorDetails: String)
}