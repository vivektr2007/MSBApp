package com.msb.presentation.main.userprofile

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class UserProfileMainPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> UserProfileFragment()
            1 -> AnonymousUserProfileFragment()
            else -> AnonymousUserProfileFragment()
        }
    }
}