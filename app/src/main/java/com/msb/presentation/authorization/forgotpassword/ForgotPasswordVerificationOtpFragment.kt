package com.msb.presentation.authorization.forgotpassword

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.navigation.fragment.findNavController
import com.msb.R
import com.msb.databinding.VerificationOtpFragmentBinding
import com.msb.presentation.authorization.signup.RegisterUserViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import xyz.teknol.core.auth.domain.AuthFailure
import xyz.teknol.core.domain.Failure
import xyz.teknol.database.SharedPreferenceManager
import xyz.teknol.utils.base.BaseFragment
import xyz.teknol.utils.extensions.*
import xyz.teknol.utils.validator.ValidatorBuilder

class ForgotPasswordVerificationOtpFragment : BaseFragment<VerificationOtpFragmentBinding>() {

    private lateinit var validator: ValidatorBuilder
    private val registerUserViewModel by viewModel<RegisterUserViewModel>()
    private val preferenceManager: SharedPreferenceManager by inject()
    private var enterOtp: String = ""
    private var mobilrNumber: String = ""
    private var selectedCountryCode: String = ""
    override fun getLayoutRes(): Int = R.layout.verification_otp_fragment
    private val onKeyListener =
        View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_DEL) {
                when (v.id) {
                    R.id.editTextMs1 -> if (binding.editTextMs1.text.isEmpty()) {
                        binding.editTextMs.requestFocus()
                        binding.editTextMs.selectAll()
                    }
                    R.id.editTextM2 -> if (binding.editTextM2.text.isEmpty()) {
                        binding.editTextMs1.requestFocus()
                        binding.editTextMs1.selectAll()
                    }
                    R.id.editTextMs3 -> if (binding.editTextMs3.text.isEmpty()) {
                        binding.editTextM2.requestFocus()
                        binding.editTextM2.selectAll()
                    }
                    R.id.editTextMs4 -> if (binding.editTextMs4.text.isEmpty()) {
                        binding.editTextMs3.requestFocus()
                        binding.editTextMs3.selectAll()
                    }
                    R.id.editTextMs5 -> if (binding.editTextMs5.text.isEmpty()) {
                        binding.editTextMs4.requestFocus()
                        binding.editTextMs4.selectAll()
                    }
                }
            }
            false
        }

    override fun activityCreated() {
        registerUserViewModel.failure.observe(this) {
            when (it) {
                is Failure.ServerError -> showError(it.message)
                is Failure.NetworkConnection -> showError(it.message)
                is AuthFailure.UnknownError -> showError(it.message)
                else -> showError("Server Error")
            }
        }

        registerUserViewModel.verifyOtpData.observe(this) {
            if (it.message.equals("true")) {
                val bundle = Bundle()
                bundle.putString("MobileNo", requireArguments().getString("MobileNo"))
                bundle.putString(
                    "selectedCountryCode",
                    requireArguments().getString("selectedCountryCode")
                )
                findNavController().navigate(
                    R.id.action_forgotPasswordVerificationOtpFragment_to_changePasswordFragment,
                    bundle
                )
            } else {
                showToast(it.error!!)
            }
        }
        registerUserViewModel.loader.observe(this) {
            if (it) showProgressDialog() else hideProgressDialog()
        }
    }

    private fun showError(s: String?) {
        s?.let { showSnackBar(it) }
    }

    override fun onFragmentAttach(context: Context) {
        mobilrNumber = requireArguments().getString("MobileNo")!!
        selectedCountryCode = requireArguments().getString("selectedCountryCode")!!
    }

    override fun onViewReady(savedInstanceState: Bundle?) {
        validator = ValidatorBuilder()
            .addField(
                binding.editTextMs,
                { s -> s.isNotEmpty() },
                getString(R.string.can_not_empty)
            ).addField(
                binding.editTextMs1,
                { s -> s.isNotEmpty() },
                getString(R.string.can_not_empty)
            ).addField(
                binding.editTextM2,
                { s -> s.isNotEmpty() },
                getString(R.string.can_not_empty)
            ).addField(
                binding.editTextMs3,
                { s -> s.isNotEmpty() },
                getString(R.string.can_not_empty)
            ).addField(
                binding.editTextMs4,
                { s -> s.isNotEmpty() },
                getString(R.string.can_not_empty)
            ).addField(
                binding.editTextMs5,
                { s -> s.isNotEmpty() },
                getString(R.string.can_not_empty)
            )
        binding.resendOtpTv.doGone()
        binding.nextTv.doVisible()
        setListeners()
    }

    private fun setListeners() {
        binding.nextTv.setOnClickListener {
            enterOtp =
                binding.editTextMs.getString() + binding.editTextMs1.getString() + binding.editTextM2.getString() +
                        binding.editTextMs3.getString() + binding.editTextMs4.getString() + binding.editTextMs5.getString()
            if (validator.validate()) {
                registerUserViewModel.verifyOtp(selectedCountryCode, mobilrNumber, enterOtp)
            }
        }
        binding.editTextMs.afterTextChanged {
            if (it.isNotEmpty())
                binding.editTextMs1.requestFocus();
        }
        binding.editTextMs1.afterTextChanged {
            if (it.isNotEmpty())
                binding.editTextM2.requestFocus();
        }
        binding.editTextM2.afterTextChanged {
            if (it.isNotEmpty())
                binding.editTextMs3.requestFocus();
        }
        binding.editTextMs3.afterTextChanged {
            if (it.isNotEmpty())
                binding.editTextMs4.requestFocus();
        }
        binding.editTextMs4.afterTextChanged {
            if (it.isNotEmpty())
                binding.editTextMs5.requestFocus();
        }
        binding.editTextMs1.setOnKeyListener(onKeyListener)
        binding.editTextM2.setOnKeyListener(onKeyListener)
        binding.editTextMs3.setOnKeyListener(onKeyListener)
        binding.editTextMs4.setOnKeyListener(onKeyListener)
        binding.editTextMs5.setOnKeyListener(onKeyListener)
    }

}