package com.msb.presentation.main.userprofile

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.navigation.fragment.findNavController
import com.msb.R
import com.msb.databinding.UserProfileFragmentBinding
import com.msb.framework.GlideApp
import com.msb.presentation.authorization.AuthorizationActivity
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import xyz.teknol.core.auth.domain.AuthFailure
import xyz.teknol.core.auth.domain.GetUserProfileData
import xyz.teknol.core.domain.Failure
import xyz.teknol.database.SharedPreferenceManager
import xyz.teknol.utils.base.BaseFragment
import xyz.teknol.utils.extensions.*
import xyz.teknol.utils.validator.ValidatorBuilder
import java.io.File


class UserProfileFragment : BaseFragment<UserProfileFragmentBinding>() {

    private var profilePic: String = ""
    private lateinit var validator: ValidatorBuilder
    private val viewModel: UserProfileViewModel by viewModel()
    private val preferenceManager: SharedPreferenceManager by inject()
    val ageItems = ArrayList<String>()
    val genderItems = arrayOf("Male", "Female", "Transgender")
    val interestedInItems = arrayOf("Male", "Female", "Transgender")
    private var ageValue: String = ""
    private var genderValue: String = ""
    private var interestedInValue: String = ""
    private lateinit var filePhoto: File
    override fun getLayoutRes(): Int = R.layout.user_profile_fragment

    override fun activityCreated() {
        for (i in 10..60) {
            ageItems.add(i.toString())
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
        viewModel.userProfile.observe(this) {
            setUpUserProfileData(it)
        }

        viewModel.uploadUserProfileData.observe(this) {
            profilePic = it.message.toString()
            if (it.status == 200) {
                showToast("User Profile Pic Updated successfully")
            }
        }
        viewModel.updateUserProfile.observe(this) {
            showToast("Profile Updated successfully")
            // doBack()
        }
    }

    private fun setUpUserProfileData(getUserProfileData: GetUserProfileData) {
        binding.firtName.setText(getUserProfileData.name)
        binding.lastName.setText("")
        binding.address.setText(getUserProfileData.address!!)
        binding.relationship.setText(getUserProfileData.relationship!!)
        binding.profession.setText(getUserProfileData.profession!!)
        binding.companyCollege.setText(getUserProfileData.companyUniversityCollege!!)
        binding.profilePost.setText(getUserProfileData.profile!!)

        val genderAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item, genderItems
        )

        binding.genderSpinner.adapter = genderAdapter

        val interestedInAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item, interestedInItems
        )

        binding.interestedInSpinner.adapter = interestedInAdapter
        val ageAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item, ageItems
        )

        binding.ageSpinner.adapter = ageAdapter
        for (i in ageItems.indices) {
            if (ageItems[i] == getUserProfileData.age.toString()) {
                binding.ageSpinner.setSelection(i)
                break
            }
        }

        for (i in genderItems.indices) {
            if (genderItems[i] == getUserProfileData.gender.toString()) {
                binding.genderSpinner.setSelection(i)
                break
            }
        }
        for (i in interestedInItems.indices) {
            if (interestedInItems[i] == getUserProfileData.interestedIn.toString()) {
                binding.interestedInSpinner.setSelection(i)
                break
            }
        }



        GlideApp
            .with(binding.userImage)
            .load(getUserProfileData.profilePicFullPath)
            .into(binding.userImage)
    }

    private fun showError(error: String?) {
        error?.let { showSnackBar(it) }
    }

    private fun setListeners() {

        binding.btnSubmit.setOnClickListener {
            if (validator.validate()) {
                requireActivity().hideKeyboard()
                viewModel.updateUserProfile(
                    binding.inputLayoutFirstName.getString(),
                    binding.inputLayoutLastName.getString(),
                    binding.ageSpinner.selectedItem.toString().toInt(),
                    binding.genderSpinner.selectedItem.toString(),
                    binding.inputLayoutRelationship.getString(),
                    binding.inputLayoutAddress.getString(),
                    binding.inputLayoutProfession.getString(),
                    binding.inputLayoutProfilePost.getString(),
                    binding.inputLayoutCompany.getString(),
                    binding.inputLayoutProfilePost.getString(),
                    binding.interestedInSpinner.selectedItem.toString(),
                    preferenceManager.getMobile()!!.toLong(),
                    preferenceManager.getMsbPin()!!.toInt(),
                    preferenceManager.getEmail()!!
                )
            }
        }

        binding.ageSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position == 0)
                //showToast("")
                else
                    ageValue = id.toString()
                //showToast(position.toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        binding.genderSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position == 0)
                //showToast("")
                else
                    genderValue = id.toString()

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        binding.interestedInSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (position == 0)
                    //showToast("")
                    else
                        interestedInValue = id.toString()
                    //showToast(position.toString())
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        binding.changePasswordTv.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("MobileNo", preferenceManager.getMobile())
            bundle.putString("FromUserProfile", "FromUserProfile")
            findNavController().navigate(
                R.id.action_userProfileMainFragment_to_changePasswordFragment1, bundle
            )
        }

        binding.userImage.setOnClickListener {
//            openCamera()
            openGallery()
        }
    }

    private fun openGallery() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (requireContext().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                requestPermissions(permissions, PERMISSION_CODE)
            } else {
                chooseImageGallery();

            }
        } else {
            chooseImageGallery();

        }
    }

    private fun chooseImageGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE_GALLERY)
    }

    private fun openCamera() {

        val takePhotoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        filePhoto = getPhotoFile(Companion.FILE_NAME)
        val providerFile =
            FileProvider.getUriForFile(
                requireContext(),
                "com.msb.androidcamera.fileprovider",
                filePhoto
            )
        takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, providerFile)
        if (takePhotoIntent.resolveActivity(requireContext().packageManager) != null) {
            startActivityForResult(takePhotoIntent, REQUEST_CODE)
        } else {
            Toast.makeText(requireContext(), "Camera could not open", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getPhotoFile(fileName: String): File {
        val directoryStorage = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(fileName, ".jpg", directoryStorage)
    }

    override fun onFragmentAttach(context: Context) {

    }

    override fun onViewReady(savedInstanceState: Bundle?) {
        viewModel.getUserProfile(preferenceManager.getId()!!)
        binding.btnLogout.setOnClickListener {
            SharedPreferenceManager(requireContext()).setLoggedIn(false)
            startActivity(Intent(requireContext(), AuthorizationActivity::class.java))
            requireActivity().finishAffinity()
        }

        validator = ValidatorBuilder()
            .addField(
                binding.inputLayoutFirstName,
                { s -> s.isNotEmpty() },
                getString(R.string.please_enter_first_name)
            )
//            .addField(
//                binding.inputLayoutLastName,
//                { s -> s.isNotEmpty() },
//                getString(R.string.please_enter__last_name)
//            )
            .addField(
                binding.inputLayoutRelationship,
                { s -> s.isNotEmpty() },
                getString(R.string.please_enter_relationship)
            ).addField(
                binding.inputLayoutAddress,
                { s -> s.isNotEmpty() },
                getString(R.string.please_enter_address)
            ).addField(
                binding.inputLayoutCompany,
                { s -> s.isNotEmpty() },
                getString(R.string.please_enter_company)
            )

        setListeners()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val takenPhoto = BitmapFactory.decodeFile(filePhoto.absolutePath)
            binding.userImage.setImageBitmap(takenPhoto)
            viewModel.uploadFile(filePhoto.absolutePath, preferenceManager.getId()!!)
        } else if (requestCode == REQUEST_CODE_GALLERY && resultCode == Activity.RESULT_OK) {
            val selectedImageUri: Uri? = data?.data
            val picturePath = getPath(
                requireContext(),
                selectedImageUri!!
            )
            binding.userImage.setImageURI(data.data)
            viewModel.uploadFile(picturePath, preferenceManager.getId()!!)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun getPath(context: Context, uri: Uri): String {
        var result: String? = null
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor? = context.contentResolver.query(uri, proj, null, null, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                val column_index: Int = cursor.getColumnIndexOrThrow(proj[0])
                result = cursor.getString(column_index)
            }
            cursor.close()
        }
        if (result == null) {
            result = "Not found"
        }
        return result
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    chooseImageGallery()
                } else {
                    Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    companion object {
        private const val REQUEST_CODE = 13
        private const val REQUEST_CODE_GALLERY = 14
        private const val FILE_NAME = "photo.jpg"
        private val IMAGE_CHOOSE = 1000;
        private val PERMISSION_CODE = 1001;
    }
}