package com.msb.presentation.main.chat

import android.Manifest
import android.content.Context
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.msb.R
import com.msb.databinding.ChatListFragmentBinding
import com.msb.presentation.main.chat.adapter.ChatAdapter
import contacts.core.Contacts
import contacts.core.util.phoneList
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import xyz.teknol.core.auth.domain.AuthFailure
import xyz.teknol.core.domain.Failure
import xyz.teknol.core.user.domain.UserListDataItem
import xyz.teknol.core.user.domain.UserListParams
import xyz.teknol.database.SharedPreferenceManager
import xyz.teknol.utils.base.BaseFragment
import xyz.teknol.utils.extensions.hideProgressDialog
import xyz.teknol.utils.extensions.showProgressDialog
import xyz.teknol.utils.extensions.showSnackBar
import xyz.teknol.utils.extensions.showToast
import xyz.teknol.utils.permission.PermissionDelegates

class ChatListFragment : BaseFragment<ChatListFragmentBinding>(), ChatAdapter.Callback {

    override fun getLayoutRes(): Int = R.layout.chat_list_fragment


    /* Refer to the Get-Started guide to create two users and their access tokens
     * https://mesibo.com/documentation/tutorials/get-started/
     */


    private val preferenceManager: SharedPreferenceManager by inject()
    private val viewModel: ChatListViewModel by viewModel()
    private val adapter = ChatAdapter(this)


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
        viewModel.userList.observe(this) {
            adapter.setList(it)
        }
    }

    private fun showError(message: String?) {
        message?.let { showSnackBar(it) }
    }

    override fun onFragmentAttach(context: Context) {
    }

    override fun onViewReady(savedInstanceState: Bundle?) {

        askForPermissions(object : PermissionDelegates {
            override fun onPermissionGranted(permissions: List<String>) {
                val contacts = Contacts(requireContext()).query().find()
                val params = ArrayList<UserListParams>()
                for (i in 0 until contacts.size) {
                    if (contacts[i].hasPhoneNumber == true) {
                        for (j in 0 until contacts[i].phoneList().size) {
                            if (contacts[i].phoneList()[j].number != null) {
                                val re = Regex("[^0-9]")
                                val number = re.replace(contacts[i].phoneList()[j].number!!, "")
                                params.add(
                                    UserListParams(
                                        number
                                    )
                                )
                            }
                        }
                    }
                }
                viewModel.getUserList(params)
            }

            override fun onPermissionDenied(permissions: List<String>) {
                showToast("Please provide contacts permission to view Chats")
            }

        }, arrayOf(Manifest.permission.READ_CONTACTS))
        binding.recyclerView.adapter = adapter
    }

    override fun onItemClicked(userListDataItem: UserListDataItem) {
        val jsonString = Gson().toJson(userListDataItem)
        val bundle = bundleOf("userListDataItem" to jsonString)

        findNavController().navigate(R.id.action_chatListFragment_to_chatMessagesActivity, bundle)
    }

}