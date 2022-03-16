package com.msb.presentation.authorization.forgotpassword

import android.content.Context
import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.msb.R
import com.msb.databinding.ForgotPasswordFragmentBinding
import com.msb.presentation.authorization.signup.RegisterUserViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import xyz.teknol.core.auth.domain.AuthFailure
import xyz.teknol.core.domain.Failure
import xyz.teknol.utils.base.BaseFragment
import xyz.teknol.utils.extensions.*
import xyz.teknol.utils.validator.ValidatorBuilder

class ForgotPasswordFragment : BaseFragment<ForgotPasswordFragmentBinding>() {
    private lateinit var validator: ValidatorBuilder
    private val registerUserViewModel by viewModel<RegisterUserViewModel>()
    private var Otp: String = ""
    private var enterOtp: String = ""
    override fun getLayoutRes(): Int = R.layout.forgot_password_fragment

    override fun activityCreated() {
        registerUserViewModel.failure.observe(this) {
            when (it) {
                is Failure.ServerError -> showError(it.message)
                is Failure.NetworkConnection -> showError(it.message)
                is AuthFailure.UnknownError -> showError(it.message)
                else -> showError("Server Error")
            }
        }

        registerUserViewModel.otpData.observe(this) {
            showToast(it.otp!!)
            val bundle = Bundle()
            bundle.putString("selectedCountryCode", binding.ccp.selectedCountryCode)
            bundle.putString("MobileNo", binding.phoneNumberEt.getString())
            bundle.putString("Otp", it.otp)
            findNavController().navigate(
                R.id.action_forgotPasswordFragment_to_forgotPasswordVerificationOtpFragment,
                bundle
            )

        }
        registerUserViewModel.loader.observe(this) {
            if (it) showProgressDialog() else hideProgressDialog()
        }
    }

    private fun showError(s: String?) {
        showToast(s.toString())
    }

    override fun onFragmentAttach(context: Context) {

    }

    override fun onViewReady(savedInstanceState: Bundle?) {
        validator = ValidatorBuilder()
            .addField(
                binding.inputLayoutPhone,
                { s -> s.phoneValidator() },
                getString(R.string.please_enter_valid_phone)
            )
        setListeners()
    }

    private fun setListeners() {
        binding.btnGetOtp.setOnClickListener {
            if (validator.validate()) {
                registerUserViewModel.getOtp(
                    binding.ccp.selectedCountryCode,
                    binding.inputLayoutPhone.getString()
                )
            }
        }
    }
}