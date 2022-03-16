package com.msb.presentation.authorization.success

import android.content.Context
import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.msb.R
import com.msb.databinding.ChangeMsbpinSuccessFragmentBinding
import xyz.teknol.utils.base.BaseFragment

class ChangeMsbpinSuccessFragment : BaseFragment<ChangeMsbpinSuccessFragmentBinding>() {
    private var isFrom: String = ""
    override fun getLayoutRes(): Int = R.layout.change_msbpin_success_fragment

    override fun activityCreated() {
    }

    override fun onFragmentAttach(context: Context) {
        isFrom = requireArguments().getString("FromUserProfile")!!

    }

    override fun onViewReady(savedInstanceState: Bundle?) {
        setListeners()
    }

    private fun setListeners() {
        if (isFrom == "FromUserProfile") {
            binding.button.setOnClickListener {
                findNavController().navigate(
                    R.id.action_changeMsbpinSuccessFragment_to_loginFragment2
                )
            }
            requireActivity().finish()
        } else {
            binding.button.setOnClickListener {
                findNavController().popBackStack(R.id.loginFragment, false)
            }
        }

    }

}