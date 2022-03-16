package com.msb.presentation.authorization.signup

import android.content.Context
import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.msb.R
import com.msb.databinding.RegisterUserFragmentBinding
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import xyz.teknol.core.auth.domain.AuthFailure
import xyz.teknol.core.domain.Failure
import xyz.teknol.database.SharedPreferenceManager
import xyz.teknol.utils.base.BaseFragment
import xyz.teknol.utils.extensions.*
import xyz.teknol.utils.validator.ValidatorBuilder

class RegisterUserFragment : BaseFragment<RegisterUserFragmentBinding>() {

    private lateinit var validator: ValidatorBuilder
    private val viewModel by viewModel<RegisterUserViewModel>()

    private val preferenceManager: SharedPreferenceManager by inject()
    override fun getLayoutRes(): Int = R.layout.register_user_fragment

    override fun activityCreated() {
//        viewModel.failure.observe(this) {
//            when (it) {
//                is Failure.ServerError -> showError(it.message)
//                is Failure.NetworkConnection -> showError(it.message)
//                is AuthFailure.UnknownError -> showError(it.message)
//                is Failure.DataBaseError -> showError(it.message)
//                else -> showError("Server Error")
//            }
//        }
//        viewModel.loader.observe(this) {
//            if (it) showProgressDialog() else hideProgressDialog()
//        }
//        viewModel.signUpData.observe(this) {
//            showToast("User Registered Successfully")
//            preferenceManager.setLoggedIn(true)
//            preferenceManager.setId(it.id!!)
//            preferenceManager.setMobile(it.mobileNo.toString())
//            preferenceManager.setMsbPin(it.msbPin.toString())
//            preferenceManager.setEmail(it.emailId!!)
//
//        }
    }

    private fun showError(message: String?) {
        message?.let { showSnackBar(it) }
    }

    override fun onFragmentAttach(context: Context) {

    }

    override fun onViewReady(savedInstanceState: Bundle?) {

        validator = ValidatorBuilder()
            .addField(
                binding.inputLayoutEnterName,
                { s -> s.isNotEmpty() },
                getString(R.string.please_enter_name)
            )
            .addField(
                binding.inputLayoutEmail,
                { s -> s.emailValidator() },
                getString(R.string.please_enter_valid_email)
            )
            .addField(
                binding.inputLayoutPhone,
                { s -> s.phoneValidator() },
                getString(R.string.please_enter_valid_phone)
            )
            .addField(
                binding.inputLayoutMsbpin,
                { s -> s.lengthValidator(6) },
                "Min 6 numbers required"
            )
            .addField(
                binding.inputLayoutConfirmMsbpin,
                { s -> s == binding.inputLayoutMsbpin.editText?.text.toString() },
                "MSBPIN mismatch"
            )
        setListeners()
    }

    private fun setListeners() {

        binding.btnCreateAccount.setOnClickListener {
            if (validator.validate()) {
                requireActivity().hideKeyboard()
                val bundle = Bundle()
                bundle.putString("Name", binding.inputLayoutEnterName.getString())
                bundle.putString("Email", binding.inputLayoutEmail.getString())
                bundle.putString("Phone", binding.inputLayoutPhone.getString())
                bundle.putString("Msbpin", binding.inputLayoutMsbpin.getString())
                bundle.putString("selectedCountryCode", binding.ccp.selectedCountryCode)
                findNavController().navigate(
                    R.id.action_registerUserFragment_to_verificationOtpFragment,
                    bundle
                )

//                viewModel.signUp(
//                    binding.inputLayoutEnterName.getString(),
//                    binding.inputLayoutEmail.getString(),
//                    binding.inputLayoutPhone.getString(),
//                    binding.inputLayoutMsbpin.getString(),
//                    binding.ccp.selectedCountryCode
//                )
            }
        }
        binding.btnGoToLogin.setOnClickListener {
            doBack()
        }
    }
}