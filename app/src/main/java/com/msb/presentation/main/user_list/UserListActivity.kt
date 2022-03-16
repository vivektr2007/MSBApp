package com.msb.presentation.main.user_list

import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import com.msb.R
import com.msb.databinding.ActivityUserListBinding
import xyz.teknol.utils.base.BaseActivity

class UserListActivity : BaseActivity<ActivityUserListBinding>() {

    override fun getLayoutRes(): Int {
        return R.layout.activity_user_list
    }

    override fun onViewReady(savedInstanceState: Bundle?) {
        binding.viewPager.adapter = UserListMainPagerAdapter(supportFragmentManager, lifecycle)
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            if (position == 0) {
                tab.text = "Phonebook"
            } else {
                tab.text = "Anon Friends"
            }
        }.attach()
        setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}