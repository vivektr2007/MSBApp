package com.msb.presentation.main.userprofile

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import com.msb.R
import com.msb.databinding.AnonymousUserProfileFragmentBinding
import com.msb.presentation.main.userprofile.adapter.ImageListAdapter
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import xyz.teknol.core.auth.domain.AuthFailure
import xyz.teknol.core.domain.Failure
import xyz.teknol.core.profile.domain.AnonymousProfileData
import xyz.teknol.database.SharedPreferenceManager
import xyz.teknol.utils.base.BaseFragment
import xyz.teknol.utils.callbacks.ListDialogCallback
import xyz.teknol.utils.extensions.*

class AnonymousUserProfileFragment : BaseFragment<AnonymousUserProfileFragmentBinding>(),
    ListDialogCallback {

    private val viewModel: UserProfileViewModel by viewModel()
    private val preferenceManager: SharedPreferenceManager by inject()
    private val imageListAdapter = ImageListAdapter()
    private val numberOfMessage = ArrayList<String>()
    private var selectedPic = ""
    override fun getLayoutRes(): Int = R.layout.anonymous_user_profile_fragment

    override fun activityCreated() {
        for (i in 1..60) {
            numberOfMessage.add(i.toString())
        }
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
        viewModel.anonymousUserProfilePicsList.observe(this) {
            imageListAdapter.setList(it)
        }
        viewModel.anonymousProfile.observe(this) {
            setProfileData(it)
            showToast("Anonymous User Profile Updated successfully")
        }

    }

    private fun setProfileData(it: AnonymousProfileData) {
        it.profilePic?.let { selectedPic = it }
        imageListAdapter.setSelectedItem(selectedPic)
        binding.userIdEdittext.setText(it.anonymousUserId)
        binding.locationCheckBox.isChecked = it.anonymousUserNearby == true
        binding.revelIdentityCheckBox.isChecked = it.revealGender == true
        binding.numberOfMessageSpinner.text = it.smsCountPerUser
    }

    private fun showError(error: String?) {
        error?.let { showSnackBar(it) }
    }

    override fun onFragmentAttach(context: Context) {

    }

    override fun onViewReady(savedInstanceState: Bundle?) {
        viewModel.getAnonymousUserProfilePics()
        preferenceManager.getId()?.let { viewModel.getAnonymousProfile(it) }
        binding.imageList.adapter = imageListAdapter
        setListeners()
    }

    private fun setListeners() {
        binding.numberOfMessageSpinner.setOnClickListener {
            requireActivity().showListDialog(numberOfMessage, 101, this)
        }
        binding.btnSubmit.setOnClickListener {
            selectedPic = imageListAdapter.getSelectedItem()
            if (binding.userIdEdittext.text.toString().isNotEmpty()) {
                if (selectedPic.isNotEmpty()) {
                    viewModel.updateAnonymousProfile(
                        preferenceManager.getId(),
                        binding.userIdEdittext.text.toString(),
                        binding.locationCheckBox.isChecked,
                        selectedPic,
                        binding.revelIdentityCheckBox.isChecked,
                        binding.numberOfMessageSpinner.text.toString()
                    )
                } else {
                    Toast.makeText(requireContext(), "Select a Profile Pic", Toast.LENGTH_SHORT)
                        .show()
                }
            } else {
                binding.userIdEdittext.error = getString(R.string.please_enter)
            }
        }
    }

    override fun onItemSelected(position: Int, item: String, dialogId: Int) {
        binding.numberOfMessageSpinner.text = item
    }

    override fun onDismiss(dialogId: Int) {

    }

}