package xyz.teknol.googleauth

import android.app.Activity
import android.content.Intent
import xyz.teknol.googleauth.contract.GoogleContract
import xyz.teknol.googleauth.model.GoogleUser
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

class GoogleAuthenticator(private val contract: GoogleContract, webClientId: String? = "") {

    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private var gso: GoogleSignInOptions =
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(webClientId)
            .requestEmail()
            .build()


    fun login(activity: Activity): Intent {
        mGoogleSignInClient = GoogleSignIn.getClient(activity, gso)
        return mGoogleSignInClient.signInIntent
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount = completedTask.getResult(ApiException::class.java)!!
            contract.onAuthorizationSuccess(
                GoogleUser(
                    account.id!!,
                    account.idToken,
                    account.email!!,
                    account.displayName!!,
                    account.photoUrl.toString()
                )
            )
        } catch (e: ApiException) {
            contract.onAuthorizationFailed("Google Error: ${e.message}")
        }
    }

    fun handleActivityResult(data: Intent?) {
        val task: Task<GoogleSignInAccount> =
            GoogleSignIn.getSignedInAccountFromIntent(data)
        handleSignInResult(task)
    }
}