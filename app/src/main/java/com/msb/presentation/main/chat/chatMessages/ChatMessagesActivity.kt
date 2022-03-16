package com.msb.presentation.main.chat.chatMessages

import android.os.Bundle
import android.view.View
import com.google.gson.Gson
import com.mesibo.api.Mesibo
import com.mesibo.api.MesiboGroupProfile
import com.mesibo.api.MesiboProfile
import com.mesibo.calls.api.MesiboCall
import com.msb.R
import com.msb.databinding.ActivityChatMessagesBinding
import com.msb.presentation.main.chat.ChatListViewModel
import com.msb.presentation.main.chat.SendMessageViewModel
import com.msb.presentation.main.chat.chatMessages.adapter.MessageAdapter
import com.msb.presentation.utils.MessageClass
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import xyz.teknol.core.auth.domain.AuthFailure
import xyz.teknol.core.domain.Failure
import xyz.teknol.core.user.domain.UserListDataItem
import xyz.teknol.database.SharedPreferenceManager
import xyz.teknol.utils.base.BaseActivity
import xyz.teknol.utils.bindingAdapters.showSnackBar
import xyz.teknol.utils.extensions.hideProgressDialog
import xyz.teknol.utils.extensions.showProgressDialog
import xyz.teknol.utils.extensions.showSnackBar
import xyz.teknol.utils.extensions.showToast

class ChatMessagesActivity : BaseActivity<ActivityChatMessagesBinding>(), Mesibo.ConnectionListener,
    Mesibo.MessageListener,
    Mesibo.ProfileListener, Mesibo.GroupListener {
    private lateinit var objectData: UserListDataItem

    private lateinit var messageObjectData: MessageClass
    private var userListData: String = ""
    override fun getLayoutRes(): Int = R.layout.activity_chat_messages
    internal inner class DemoUser(var token: String, var name: String, var address: String)

    private val preferenceManager: SharedPreferenceManager by inject()
    private val viewModel: SendMessageViewModel by viewModel()

    private var mRemoteUser: DemoUser? = null

    //    private var mMessageClass: MessageClass? = null
    var mProfile: MesiboProfile? = null
    var mReadSession: Mesibo.ReadDbSession? = null

    private lateinit var adapter: MessageAdapter
    val mMessageList: ArrayList<MessageClass> = ArrayList()

    private fun showError(message: String?) {
        //message?.let { showSnackBar(it) }
    }

    override fun onViewReady(savedInstanceState: Bundle?) {
        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            userListData = bundle.getString("userListDataItem")!!
            objectData = Gson().fromJson(userListData, UserListDataItem::class.java)
            binding.textView21.text=objectData.name
        }

        //binding.recyclerView.adapter = MessageAdapter()
        var mUser1 = DemoUser(
            preferenceManager.getMesiboUserToken()!!,
            preferenceManager.getMobile()!!,
            preferenceManager.getMobile()!!
        )
        var mUser2 = DemoUser(
            objectData.mesiboUser!!.token!!,
            objectData.mesiboUser!!.address!!,
            objectData.mesiboUser!!.address!!
        )
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
        viewModel.sendDataList.observe(this) {

        }


//        val mUser2 =
//            DemoUser(
//                "91fc6b5bccabab7eb30d297b3dda48bb7b15996b41ba2d1fcaeff3de6f7tabd4879cbfe",
//                "Ayush",
//                "8700310021"
//            )
//        val mUser1 =
//            DemoUser(
//                "7bbbc17481ac2219105e5f8178b71fce0553909e767f3a13981e003de6f5ma5624777d3e",
//                "Anup",
//                "9015615376"
//            )

        mesiboInit(mUser1, mUser2)

        binding.sendMsgImg.setOnClickListener {
            onSendMessage(binding.sendMsgImg)
        }
        adapter = MessageAdapter(preferenceManager.getUsername()!!)
        binding.recyclerView.adapter = adapter

        binding.toolbar2.setOnClickListener {
            onBackPressed()
        }
    }

    private fun onSendMessage(view: View?) {
        // if (!isLoggedIn()) return
        val currentTimestamp = System.currentTimeMillis()
        val mMessageClass = MessageClass(
            binding.textMsgEt.text.toString().trim { it <= ' ' },
            currentTimestamp.toString(),
            preferenceManager.getUsername()!!
        )
        adapter.addMessage(mMessageClass)
        mProfile?.sendMessage(
            Mesibo.random(),
            Gson().toJson(mMessageClass)
        )

        //viewModel.sendMessage("114444213",)
        binding.textMsgEt.setText("")
        binding.recyclerView.scrollToPosition(adapter.itemCount - 1)
    }

    private fun mesiboInit(user: DemoUser, remoteUser: DemoUser) {
        val api: Mesibo = Mesibo.getInstance()
        api.init(applicationContext)
        Mesibo.addListener(this)
        Mesibo.setSecureConnection(true)
        Mesibo.setAccessToken(user.token)
        Mesibo.setDatabase("msbdb", 0)
        Mesibo.start()

        mRemoteUser = remoteUser
        mProfile = Mesibo.getProfile(remoteUser.address)
        mProfile?.setName(remoteUser.name)
        mProfile?.save()


        // Read receipts are enabled only when App is set to be in foreground
        Mesibo.setAppInForeground(applicationContext, 0, true)
        mReadSession = mProfile?.createReadSession(this)
        mReadSession?.enableReadReceipt(true)
        mReadSession?.read(100)

        /* initialize call with custom title */

        /* initialize call with custom title */
        MesiboCall.getInstance().init(applicationContext)
//        val cp = MesiboCall.getInstance().createCallProperties(true)
//        cp.ui.title = "First App"
//        MesiboCall.getInstance().setDefaultUiProperties(cp.ui)
    }

    override fun Mesibo_onConnectionStatus(status: Int) {
        // showToast("Connection Status: $status")
    }

    override fun Mesibo_onMessage(messageParams: Mesibo.MessageParams?, data: ByteArray?): Boolean {
        try {
            val message = String(data!!)
            messageObjectData = Gson().fromJson(message, MessageClass::class.java)
            adapter.addMessage(
                MessageClass(
                    messageObjectData.message,
                    messageObjectData.timeStamp,
                    messageObjectData.userName
                )
            )
            binding.recyclerView.scrollToPosition(adapter.itemCount - 1)
        } catch (e: Exception) {
        }
        return true
    }

    fun addGroupMembers(profile: MesiboProfile) {
        if (!isLoggedIn()) return
        val gp = profile.groupProfile
        val members = arrayOf(mRemoteUser!!.address)
        var mp: MesiboGroupProfile.MemberPermissions = MesiboGroupProfile.MemberPermissions();
        mp.flags = MesiboGroupProfile.MEMBERFLAG_ALL.toLong()
        mp.adminFlags = 0;
        gp.addMembers(members, mp)
    }

    fun isLoggedIn(): Boolean {
        if (Mesibo.STATUS_ONLINE == Mesibo.getConnectionStatus()) return true
        showToast("Login with a valid token first")
        return false
    }

    override fun Mesibo_onMessageStatus(messageParams: Mesibo.MessageParams) {

//        showToast("Message Status: " + messageParams.getStatus())

    }

    override fun Mesibo_onActivity(messageParams: Mesibo.MessageParams?, i: Int) {
        //showToast("Message Status: " + messageParams!!.isRealtimeMessage)
    }

    override fun Mesibo_onLocation(
        messageParams: Mesibo.MessageParams?,
        location: Mesibo.Location?
    ) {
    }

    override fun Mesibo_onFile(messageParams: Mesibo.MessageParams?, fileInfo: Mesibo.FileInfo?) {}

    override fun Mesibo_onProfileUpdated(profile: MesiboProfile) {
        //showToast(profile.getName() + " has updated profile")
    }

    override fun Mesibo_onGetProfile(profile: MesiboProfile): Boolean {
        return false;
    }

    override fun Mesibo_onGroupCreated(profile: MesiboProfile) {
        //showToast("New Group Created: " + profile.name)
        addGroupMembers(profile)
    }

    override fun Mesibo_onGroupJoined(profile: MesiboProfile) {
    }

    override fun Mesibo_onGroupLeft(profile: MesiboProfile) {
    }

    override fun Mesibo_onGroupMembers(
        profile: MesiboProfile,
        members: Array<out MesiboGroupProfile.Member>?
    ) {
    }

    override fun Mesibo_onGroupMembersJoined(
        profile: MesiboProfile,
        members: Array<out MesiboGroupProfile.Member>?
    ) {
    }

    override fun Mesibo_onGroupMembersRemoved(
        profile: MesiboProfile,
        members: Array<out MesiboGroupProfile.Member>?
    ) {
    }

    override fun Mesibo_onGroupSettings(
        p0: MesiboProfile?,
        p1: MesiboGroupProfile.GroupSettings?,
        p2: MesiboGroupProfile.MemberPermissions?,
        p3: Array<out MesiboGroupProfile.GroupPin>?
    ) {

    }

    override fun Mesibo_onGroupError(p0: MesiboProfile?, p1: Long) {

    }
}