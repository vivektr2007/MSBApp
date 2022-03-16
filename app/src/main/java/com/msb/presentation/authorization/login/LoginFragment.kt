package com.msb.presentation.authorization.login

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import android.os.Bundle
import android.os.CancellationSignal
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.msb.R
import com.msb.databinding.LoginFragmentBinding
import com.msb.presentation.authorization.login.dialogs.FingerPrintDialog
import com.msb.presentation.authorization.login.dialogs.FingerprintSuccessDialog
import com.msb.presentation.main.MainActivity
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import xyz.teknol.core.auth.domain.AuthFailure
import xyz.teknol.core.domain.Failure
import xyz.teknol.database.SharedPreferenceManager
import xyz.teknol.utils.base.BaseFragment
import xyz.teknol.utils.extensions.*
import xyz.teknol.utils.validator.ValidatorBuilder


class LoginFragment : BaseFragment<LoginFragmentBinding>() {

    private lateinit var validator: ValidatorBuilder
    private val viewModel: LoginViewModel by viewModel()
    private val preferenceManager: SharedPreferenceManager by inject()
    private lateinit var fingerPrintDialog: FingerPrintDialog
    override fun getLayoutRes(): Int = R.layout.login_fragment
    private var fingurePrintValue: Int = 0
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
            preferenceManager.setMesiboUserDetails(Gson().toJson(it.mesiboUser))
            preferenceManager.setMesiboToken(it.mesiboUser!!.token!!)
            preferenceManager.setMesiboUid(it.mesiboUser!!.uid!!)
            it.id?.let { it1 -> preferenceManager.setId(it1) }
            preferenceManager.setMobile(it.mobileNo.toString())
            preferenceManager.setMsbPin(it.msbPin.toString())
            it.emailId?.let { it1 -> preferenceManager.setEmail(it1) }
            it.name?.let { it1 -> preferenceManager.setUsername(it1) }
            startActivity(Intent(requireContext(), MainActivity::class.java))
            requireActivity().finish()
        }
        viewModel.userByFingerprintData.observe(this) {
            if (it.fingerprint!! == "2") {
                viewModel.login(
                    it.mobileNo.toString(),
                    it.msbPin.toString()
                )
            } else {
                showToast("Fingureprint not added ")
            }
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
        fingerPrintDialog =
            FingerPrintDialog(requireContext(), object : FingerPrintDialog.Callback {
                override fun onCancelClick() {
                    fingerPrintDialog.dismiss()
                }

                override fun onFingerprintSuccess() {
                    fingerPrintDialog.dismiss()
                    val fingerprintSuccessDialog = FingerprintSuccessDialog(requireContext())
                    fingerprintSuccessDialog.setOnDismissListener {
                        findNavController().navigate(R.id.action_loginFragment_to_loginWithMsbPinFragment)
                    }
                    fingerprintSuccessDialog.show()
                }

            })
    }


    private fun setListeners() {
        binding.btnNoAccount.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerUserFragment)
        }

        binding.txtForgotMsbpin.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
            //findNavController().navigate(R.id.action_loginFragment_to_selectSecurityQuestionFragment)
        }

        binding.btnLoginUsingFinger.setOnClickListener {
//            fingerPrintDialog.show()
            displayBiometricPrompt()
        }
        binding.btnLogin.setOnClickListener {
            if (validator.validate())
                viewModel.login(
                    binding.inputLayoutEnterMobile.getString(),
                    binding.inputLayoutMsbpin.getString()
                )
        }
        binding.saveForFutureCheckBox.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_selectSecurityQuestionFragment)
        }
    }

    @SuppressLint("MissingPermission")
    @TargetApi(Build.VERSION_CODES.P)
    private fun displayBiometricPrompt() {
        val promt = BiometricPrompt.Builder(context)
            .setTitle("Fingerprint Login")
            .setNegativeButton(
                "Cancel",
                requireContext().mainExecutor
            ) { _, _ ->

            }
            .build()
        promt.authenticate(CancellationSignal(), requireContext().mainExecutor, object :
            BiometricPrompt.AuthenticationCallback() {
            @RequiresApi(Build.VERSION_CODES.R)
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
                super.onAuthenticationSucceeded(result)
                fingurePrintValue = result!!.authenticationType
                //val co = result!!.cryptoObject
                //showToast(co.cipher.toString())

                viewModel.getUserByFingerprint(fingurePrintValue.toString())
//                val fingerprintSuccessDialog = FingerprintSuccessDialog(requireContext())
//                fingerprintSuccessDialog.setOnDismissListener {
//                    findNavController().navigate(R.id.action_loginFragment_to_loginWithMsbPinFragment)
//                }
//                fingerprintSuccessDialog.show()
            }
        })
    }

}