package com.msb.presentation.main

import android.content.Intent
import android.os.Bundle
import androidx.navigation.findNavController
import com.google.android.material.tabs.TabLayout
import com.msb.R
import com.msb.databinding.ActivityMainBinding
import com.msb.presentation.main.userprofile.UserProfileActivity
import xyz.teknol.utils.base.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getLayoutRes(): Int = R.layout.activity_main

    override fun onViewReady(savedInstanceState: Bundle?) {
        val navController = findNavController(R.id.nav_host_fragment)
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> {
                        navController.navigate(R.id.dashboardFragment)
                    }
                    1 -> {
                        navController.navigate(R.id.chatListFragment)
                    }
                    4 -> {
                        navController.navigate(R.id.datingListFragment)
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> {
                        navController.popBackStack(R.id.dashboardFragment, true)
                    }
                    1 -> {
                        navController.popBackStack(R.id.chatListFragment, true)
                    }
                    4 -> {
                        navController.popBackStack(R.id.datingListFragment, true)
                    }
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }

        })

        binding.imageView3.setOnClickListener {
            startActivity(Intent(this, UserProfileActivity::class.java))
        }
    }
}