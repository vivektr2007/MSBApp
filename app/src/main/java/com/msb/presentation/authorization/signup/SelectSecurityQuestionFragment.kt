package com.msb.presentation.authorization.signup

import android.content.Context
import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.msb.R
import com.msb.databinding.SelectSecurityQuestionFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import xyz.teknol.utils.base.BaseFragment
import xyz.teknol.utils.validator.ValidatorBuilder

class SelectSecurityQuestionFragment : BaseFragment<SelectSecurityQuestionFragmentBinding>() {

    private lateinit var validator: ValidatorBuilder
    private val registerUserViewModel by viewModel<RegisterUserViewModel>()
    override fun getLayoutRes(): Int = R.layout.select_security_question_fragment

    override fun activityCreated() {
//        emailVerificationViewModel.failure.observe(this, {
//            when (it) {
//                is Failure.ServerError -> showError(it.message)
//                is Failure.NetworkConnection -> showError(it.message)
//                is AuthFailure.UnknownError -> showError(it.message)
//                else -> showError("Server Error")
//            }
//        })
//
//        emailVerificationViewModel.success.observe(this, {
//            showToast(it)
//            findNavController().navigate(R.id.setUserPasswordFragment)
//        })
//        emailVerificationViewModel.loader.observe(this, {
//            if (it) showProgressDialog() else hideProgressDialog()
//        })
    }

    private fun showError(s: String?) {
        //binding.btnNext.showSnackBar(s)
    }

    override fun onFragmentAttach(context: Context) {

    }

    override fun onViewReady(savedInstanceState: Bundle?) {
//        validator = ValidatorBuilder()
//            .addField(
//                binding.inputLayoutEmail,
//                { s -> s.emailValidator() },
//                getString(R.string.please_enter_valid_email)
//            )
//
        setListeners()
    }

    private fun setListeners() {


        binding.btnSubmitQuestion.setOnClickListener {
            findNavController().navigate(R.id.action_selectSecurityQuestionFragment_to_changeMsbpinSuccessFragment)
        }
    }

}