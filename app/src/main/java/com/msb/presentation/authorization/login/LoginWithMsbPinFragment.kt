package com.msb.presentation.authorization.login

import android.content.Context
import android.os.Bundle
import com.msb.R
import com.msb.databinding.LoginWithMsbpinFragmentBinding
import xyz.teknol.utils.base.BaseFragment

class LoginWithMsbPinFragment : BaseFragment<LoginWithMsbpinFragmentBinding>() {

    override fun getLayoutRes(): Int = R.layout.login_with_msbpin_fragment

    override fun activityCreated() {
    }

    override fun onFragmentAttach(context: Context) {

    }

    override fun onViewReady(savedInstanceState: Bundle?) {
        setListeners()
    }

    private fun setListeners() {

    }

}