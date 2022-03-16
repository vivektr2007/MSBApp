package com.msb.presentation.authorization.forgotpassword

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.navigation.fragment.findNavController
import com.msb.R
import com.msb.databinding.ChangePasswordFragmentBinding
import com.msb.presentation.main.userprofile.UserProfileActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import xyz.teknol.core.auth.domain.AuthFailure
import xyz.teknol.core.domain.Failure
import xyz.teknol.utils.base.BaseFragment
import xyz.teknol.utils.extensions.getString
import xyz.teknol.utils.extensions.hideProgressDialog
import xyz.teknol.utils.extensions.showProgressDialog
import xyz.teknol.utils.extensions.showSnackBar

class ChangePasswordFragment : BaseFragment<ChangePasswordFragmentBinding>() {
    private val viewModel by viewModel<ChangePasswordViewModel>()

    override fun getLayoutRes(): Int = R.layout.change_password_fragment

    override fun activityCreated() {

        viewModel.failure.observe(this) {
            when (it) {
                is Failure.ServerError -> showError(it.message)
                is Failure.NetworkConnection -> showError(it.message)
                is AuthFailure.UnknownError -> showError(it.message)
                else -> showError("Server Error")
            }
        }

        viewModel.success.observe(this) {

            val bundle = Bundle()

            bundle.putString("FromUserProfile", "FromUserProfile")
            findNavController().navigate(
                R.id.action_changePasswordFragment_to_changeMsbpinSuccessFragment,
                bundle
            )
        }
        viewModel.loader.observe(this) {
            if (it) showProgressDialog() else hideProgressDialog()
        }
    }

    private fun showError(message: String?) {
        message?.let { showSnackBar(it) }
    }

    override fun onFragmentAttach(context: Context) {
    }

    override fun onViewReady(savedInstanceState: Bundle?) {


        binding.btnLogin.setOnClickListener {
            if (binding.inputLayoutMsbpin.getString().isNotEmpty()) {
                if (binding.inputLayoutMsbpin.getString() == binding.inputLayoutMsbpin1.getString()) {
                    viewModel.changePassword(
                        binding.inputLayoutMsbpin.getString(),
                        requireArguments().getString("MobileNo").toString()
                    )
                } else {
                    binding.inputLayoutMsbpin1.error = "Mismatch Pin"
                }
            } else {
                binding.inputLayoutMsbpin.error = "Enter Pin"
            }
        }
    }

}