package com.msb.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.msb.presentation.authorization.AuthorizationActivity
import com.msb.presentation.main.MainActivity
import contacts.core.Contacts
import org.koin.android.ext.android.inject
import xyz.teknol.database.SharedPreferenceManager

class SplashActivity : AppCompatActivity() {
    private val preferenceManager: SharedPreferenceManager by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (preferenceManager.isLoggedIn()) {
            openDashBoard()
        } else {
            startActivity(Intent(this, AuthorizationActivity::class.java))
            finish()
        }
    }

    private fun openDashBoard() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}