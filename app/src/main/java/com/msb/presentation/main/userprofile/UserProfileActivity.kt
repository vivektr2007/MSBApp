package com.msb.presentation.main.userprofile

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.msb.R
import com.msb.databinding.ActivityUserProfileBinding
import com.msb.presentation.main.user_list.UserListActivity
import xyz.teknol.utils.base.BaseActivity

class UserProfileActivity : BaseActivity<ActivityUserProfileBinding>() {


    override fun getLayoutRes(): Int = R.layout.activity_user_profile

    override fun onViewReady(savedInstanceState: Bundle?) {
        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        findViewById<Toolbar>(R.id.toolbar).setupWithNavController(
            navController,
            appBarConfiguration
        )
        setSupportActionBar(binding.toolbar)
        binding.toolbarLogo.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.profile_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        startActivity(
            Intent(this, UserListActivity::class.java)
        )
        return super.onOptionsItemSelected(item)
    }
}