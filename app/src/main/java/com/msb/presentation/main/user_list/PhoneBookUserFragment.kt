package com.msb.presentation.main.user_list

import android.Manifest
import android.content.Context
import android.os.Bundle
import com.msb.R
import com.msb.databinding.FragmentPhoneBookUserBinding
import com.msb.presentation.main.user_list.adapter.PhoneBookAdapter
import contacts.core.Contacts
import xyz.teknol.utils.base.BaseFragment
import xyz.teknol.utils.extensions.showToast
import xyz.teknol.utils.permission.PermissionDelegates

class PhoneBookUserFragment : BaseFragment<FragmentPhoneBookUserBinding>(),
    PhoneBookAdapter.Callback {

    override fun getLayoutRes(): Int {
        return R.layout.fragment_phone_book_user
    }

    override fun activityCreated() {

    }

    override fun onFragmentAttach(context: Context) {

    }

    override fun onViewReady(savedInstanceState: Bundle?) {
        askForPermissions(object : PermissionDelegates {
            override fun onPermissionGranted(permissions: List<String>) {
                val contacts = Contacts(requireContext()).query().find()
                binding.recyclerView.adapter =
                    PhoneBookAdapter(this@PhoneBookUserFragment, contacts)
            }

            override fun onPermissionDenied(permissions: List<String>) {
                showToast("Please provide contacts permission")
            }

        }, arrayOf(Manifest.permission.READ_CONTACTS))
    }

    override fun onItemClicked() {

    }

}