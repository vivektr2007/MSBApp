package com.msb.presentation.authorization

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.msb.R
import com.msb.databinding.ActivityAuthorizationBinding
import xyz.teknol.utils.base.BaseActivity

class AuthorizationActivity : BaseActivity<ActivityAuthorizationBinding>() {

    override fun getLayoutRes(): Int = R.layout.activity_authorization

    override fun onViewReady(savedInstanceState: Bundle?) {
        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        findViewById<Toolbar>(R.id.toolbar)
            .setupWithNavController(navController, appBarConfiguration)

    }
}