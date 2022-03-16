package com.msb.presentation.authorization.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.msb.R
import com.msb.databinding.LoginFragmentBinding
import com.msb.presentation.main.MainActivity
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import xyz.teknol.core.auth.domain.AuthFailure
import xyz.teknol.core.domain.Failure
import xyz.teknol.database.SharedPreferenceManager
import xyz.teknol.utils.base.BaseFragment
import xyz.teknol.utils.extensions.*
import xyz.teknol.utils.validator.ValidatorBuilder


class AddFingureprintFragment : BaseFragment<LoginFragmentBinding>() {

    private lateinit var validator: ValidatorBuilder
    private val viewModel: LoginViewModel by viewModel()
    private val preferenceManager: SharedPreferenceManager by inject()

    override fun getLayoutRes(): Int = R.layout.login_fragment

    override fun activityCreated() {

        viewModel.failure.observe(this) {
            when (it) {
                is Failure.ServerError -> showError(it.message)
                is Failure.NetworkConnection -> showError(it.message)
                is AuthFailure.UnknownError -> showError(it.message)
                is Failure.DataBaseError -> showError(it.message)
                else -> showError("Server Error")
            }
        }
        viewModel.loader.observe(this) {
            if (it) showProgressDialog() else hideProgressDialog()
        }
        viewModel.loginUser.observe(this) {
            showToast("User Logged In Successfully")
            preferenceManager.setLoggedIn(true)
            it.id?.let { it1 -> preferenceManager.setId(it1) }
            preferenceManager.setMobile(it.mobileNo.toString())
            preferenceManager.setMsbPin(it.msbPin.toString())
            it.emailId?.let { it1 -> preferenceManager.setEmail(it1) }
            startActivity(Intent(requireContext(), MainActivity::class.java))
            requireActivity().finish()
        }

    }

    private fun showError(message: String?) {
        if (message!!.contains("Invalid fingerprint data")) {

        } else {
            showSnackBar(message)
        }

    }

    override fun onFragmentAttach(context: Context) {

    }

    override fun onViewReady(savedInstanceState: Bundle?) {

        validator = ValidatorBuilder()
            .addField(
                binding.inputLayoutEnterMobile,
                { s -> s.phoneValidator() },
                getString(R.string.please_enter_valid_phone)
            )
            .addField(
                binding.inputLayoutMsbpin,
                { s -> s.lengthValidator(6) },
                "Min 6 numbers required"
            )
        setListeners()

    }


    private fun setListeners() {


        binding.btnLogin.setOnClickListener {
            if (validator.validate())
                viewModel.login(
                    binding.inputLayoutEnterMobile.getString(),
                    binding.inputLayoutMsbpin.getString()
                )
        }

    }

}