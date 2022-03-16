package com.msb.presentation.main.userprofile

import android.content.Context
import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import com.msb.R
import com.msb.databinding.UserProfileMainFragmentBinding
import xyz.teknol.utils.base.BaseFragment

class UserProfileMainFragment : BaseFragment<UserProfileMainFragmentBinding>() {

    override fun getLayoutRes(): Int = R.layout.user_profile_main_fragment

    override fun activityCreated() {

    }

    override fun onFragmentAttach(context: Context) {

    }

    override fun onViewReady(savedInstanceState: Bundle?) {
        binding.viewPager.adapter = UserProfileMainPagerAdapter(this)
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            if (position == 0) {
                tab.text = "Real Profile"
            } else {
                tab.text = "Anonymous Profile"
            }
        }.attach()

    }
}