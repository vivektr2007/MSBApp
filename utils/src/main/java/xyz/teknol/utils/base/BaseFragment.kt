package xyz.teknol.utils.base

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import xyz.teknol.utils.callbacks.AlertDialogCallback
import xyz.teknol.utils.extensions.showAlert
import xyz.teknol.utils.permission.PermissionDelegates

abstract class BaseFragment<DataBinding : ViewDataBinding> : Fragment(), LifecycleObserver {

    abstract fun getLayoutRes(): Int
    abstract fun activityCreated()
    abstract fun onFragmentAttach(context: Context)
    abstract fun onViewReady(savedInstanceState: Bundle?)
    private lateinit var permissionDelegate: PermissionDelegates
    protected lateinit var binding: DataBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        try {
            binding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false)
        } catch (e: Exception) {
            if (e is Resources.NotFoundException) {
                throw RuntimeException("Invalid Layout Resource Id")
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewReady(savedInstanceState)
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreated() {
        activity?.lifecycle?.removeObserver(this)
        activityCreated()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity?.lifecycle?.addObserver(this)
        onFragmentAttach(context)
    }


    private val requestPermissionsLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            val permissionPair = bifurcatePermissions(permissions)
            when {
                permissionPair.second.isNullOrEmpty() -> {
                    permissionDelegate.onPermissionGranted(permissionPair.first)
                }
                permissionPair.first.isNullOrEmpty() -> {
                    permissionDelegate.onPermissionDenied(permissionPair.second)
                }
                else -> {
                    permissionDelegate.onPermissionGranted(permissionPair.first)
                    permissionDelegate.onPermissionDenied(permissionPair.second)
                }
            }
        }

    private fun bifurcatePermissions(permissions: MutableMap<String, Boolean>): Pair<List<String>, List<String>> {
        val grantedPermissions = ArrayList<String>()
        val deniedPermissions = ArrayList<String>()
        permissions.entries.forEach {
            val permissionName = it.key
            val isGranted = it.value
            if (!isGranted) {
                deniedPermissions.add(permissionName)
            } else {
                grantedPermissions.add(permissionName)
            }
        }
        return Pair(grantedPermissions, deniedPermissions)
    }

    fun askForPermissions(delegates: PermissionDelegates, permissions: Array<String>) {
        permissionDelegate = delegates
        isPermissionDeclaredInManifest(permissions)

        val allGranted = isAllPermissionsGranted(permissions)

        if (!allGranted)
            requestPermissions(permissions)
        else
            delegates.onPermissionGranted(permissions.toList())
    }

    fun askForPermissionsWithRationale(
        delegates: PermissionDelegates,
        permissions: Array<String>,
        message: String
    ) {
        permissionDelegate = delegates
        isPermissionDeclaredInManifest(permissions)

        val allGranted = isAllPermissionsGranted(permissions)

        if (!allGranted)
            requireActivity().showAlert(
                "Permission Required!!",
                message,
                1001,
                object : AlertDialogCallback {
                    override fun onPositiveButton(dialogId: Int) {
                        requestPermissions(permissions)
                    }

                    override fun onNegativeButton(dialogId: Int) {
                        delegates.onPermissionDenied(permissions.toList())
                    }

                })
    }

    private fun requestPermissions(permissions: Array<String>) {
        requestPermissionsLauncher.launch(
            permissions
        )
    }

    private fun isAllPermissionsGranted(permissions: Array<String>): Boolean {
        var allGranted = true
        permissions.forEach {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    it
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                allGranted = false
            }
        }
        return allGranted
    }

    private fun isPermissionDeclaredInManifest(permissions: Array<String>) {
        val packageInfo: PackageInfo =
            requireActivity().packageManager.getPackageInfo(
                requireActivity().packageName,
                PackageManager.GET_PERMISSIONS
            )
        val requestedPermissions = packageInfo.requestedPermissions

        permissions.forEach {
            if (!requestedPermissions.contains(it)) {
                throw RuntimeException("$it is not declared in app manifest")
            }
        }
    }
}