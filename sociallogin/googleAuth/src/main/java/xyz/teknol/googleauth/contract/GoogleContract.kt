package xyz.teknol.googleauth.contract

import android.content.Intent
import xyz.teknol.googleauth.model.GoogleUser

interface GoogleContract {

    fun authorize(): Intent

    fun handleResult(data: Intent?)

    fun onAuthorizationSuccess(userDetails: GoogleUser)

    fun onAuthorizationFailed(errorDetails: String)
}