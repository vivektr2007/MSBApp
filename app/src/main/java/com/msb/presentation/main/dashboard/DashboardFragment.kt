package com.msb.presentation.main.dashboard

import android.content.Context
import android.os.Bundle
import com.msb.R
import com.msb.databinding.DashboardFragmentBinding
import com.msb.presentation.authorization.login.dialogs.CreateTotdDialog
import com.msb.presentation.authorization.signup.RegisterUserViewModel
import com.msb.presentation.main.dashboard.adapter.PostAdapter
import com.msb.presentation.main.dashboard.adapter.StatusAdapter
import com.msb.presentation.main.dashboard.adapter.StoryAdapter
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import xyz.teknol.core.auth.domain.AuthFailure
import xyz.teknol.core.domain.Failure
import xyz.teknol.database.SharedPreferenceManager
import xyz.teknol.utils.base.BaseFragment
import xyz.teknol.utils.extensions.hideProgressDialog
import xyz.teknol.utils.extensions.showProgressDialog
import xyz.teknol.utils.extensions.showSnackBar
import xyz.teknol.utils.extensions.showToast

class DashboardFragment : BaseFragment<DashboardFragmentBinding>() {

    private val viewModel by viewModel<DashBoradViewModel>()
    private val preferenceManager: SharedPreferenceManager by inject()
    private lateinit var createTotdDialog: CreateTotdDialog
    override fun getLayoutRes(): Int = R.layout.dashboard_fragment

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
        viewModel.createTodData.observe(this) {
            showToast(it.totdContent!!)
            createTotdDialog.dismiss()
        }
    }

    private fun showError(error: String?) {
        error?.let { showSnackBar(it) }
    }

    override fun onFragmentAttach(context: Context) {

    }

    override fun onViewReady(savedInstanceState: Bundle?) {
        binding.statusRecyclerView.adapter = StatusAdapter()
        binding.storyRecyclerView.adapter = StoryAdapter()
        binding.postRecyclerView.adapter = PostAdapter()

        setListner()

    }

    private fun setListner() {

        binding.imageView8.setOnClickListener {

        }

        binding.editTextTextMultiLine.setOnClickListener {
            createTotdDialog =
                CreateTotdDialog(requireContext(), object : CreateTotdDialog.Callback {
                    override fun onCreateTod(string: String) {
                        viewModel.createTod(preferenceManager.getId(), string)
                    }
                })
            createTotdDialog.show()
        }


    }

}