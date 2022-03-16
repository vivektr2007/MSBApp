package xyz.teknol.inappupdate

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import xyz.teknol.inappupdate.delegates.InAppUpdateDelegates
import xyz.teknol.utils.base.BaseActivity
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability

private const val IMMEDIATE_UPDATE_REQUEST_CODE = 101
private const val FLEXIBLE_UPDATE_REQUEST_CODE = 102

enum class UpdatePriority {
    LOW,
    MEDIUM,
    HIGH
}

abstract class InAppUpdateActivity<DB : ViewDataBinding> : BaseActivity<DB>() {

    private lateinit var inAppDelegate: InAppUpdateDelegates
    private lateinit var appUpdateInfo: AppUpdateInfo
    private lateinit var appUpdateManager: AppUpdateManager
    private lateinit var listener: InstallStateUpdatedListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkUpdate()
        listener = InstallStateUpdatedListener { state ->
            if (state.installStatus() == InstallStatus.DOWNLOADING) {
                if (::inAppDelegate.isInitialized)
                    inAppDelegate.updateProgress(
                        state.bytesDownloaded(),
                        state.totalBytesToDownload()
                    )

            } else if (state.installStatus() == InstallStatus.DOWNLOADED) {
                if (::inAppDelegate.isInitialized)
                    inAppDelegate.updateDownloaded()
                else
                    popupSnackbarForCompleteUpdate()
                appUpdateManager.unregisterListener(listener)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        appUpdateManager
            .appUpdateInfo
            .addOnSuccessListener { appUpdateInfo ->
                if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                    popupSnackbarForCompleteUpdate()
                } else if (appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                    appUpdateManager.startUpdateFlowForResult(
                        appUpdateInfo,
                        AppUpdateType.IMMEDIATE,
                        this,
                        IMMEDIATE_UPDATE_REQUEST_CODE
                    )
                }
            }
    }

    private fun popupSnackbarForCompleteUpdate() {
        Snackbar.make(
            findViewById(android.R.id.content),
            getString(R.string.update_downloaded),
            Snackbar.LENGTH_INDEFINITE
        ).apply {
            setAction(getString(R.string.restart)) { appUpdateManager.completeUpdate() }
            show()
        }
    }

    private fun checkUpdate() {
        appUpdateManager = AppUpdateManagerFactory.create(this)
        val appUpdateInfoTask = appUpdateManager.appUpdateInfo
        appUpdateInfoTask.addOnSuccessListener {
            appUpdateInfo = it
            isUpdateAvailable(appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE)
        }
        appUpdateInfoTask.addOnFailureListener {
            isUpdateAvailable(false)
        }
    }

    abstract fun isUpdateAvailable(boolean: Boolean)

    protected fun getUpdatePriority(): UpdatePriority {
        return when {
            appUpdateInfo.updatePriority() >= 4 -> {
                UpdatePriority.HIGH
            }
            appUpdateInfo.updatePriority() >= 2 -> {
                UpdatePriority.MEDIUM
            }
            else -> {
                UpdatePriority.LOW
            }
        }
    }

    protected fun getUpdatePassedDays(): Int {
        return appUpdateInfo.clientVersionStalenessDays() ?: -1
    }

    protected fun startFlexibleUpdate(delegate: InAppUpdateDelegates) {
        if (!::appUpdateInfo.isInitialized)
            throw RuntimeException(getString(R.string.no_update_available))
        inAppDelegate = delegate
        appUpdateManager.registerListener(listener)
        appUpdateManager.startUpdateFlowForResult(
            appUpdateInfo,
            AppUpdateType.FLEXIBLE,
            this,
            FLEXIBLE_UPDATE_REQUEST_CODE
        )
    }

    protected fun startImmediateUpdate(delegate: InAppUpdateDelegates) {
        if (!::appUpdateInfo.isInitialized)
            throw RuntimeException(getString(R.string.no_update_available))
        inAppDelegate = delegate
        appUpdateManager.startUpdateFlowForResult(
            appUpdateInfo,
            AppUpdateType.IMMEDIATE,
            this,
            IMMEDIATE_UPDATE_REQUEST_CODE
        )
    }

    protected fun startFlexibleUpdate() {
        if (!::appUpdateInfo.isInitialized)
            throw RuntimeException(getString(R.string.no_update_available))
        appUpdateManager.registerListener(listener)
        appUpdateManager.startUpdateFlowForResult(
            appUpdateInfo,
            AppUpdateType.FLEXIBLE,
            this,
            FLEXIBLE_UPDATE_REQUEST_CODE
        )
    }

    protected fun startImmediateUpdate() {
        if (!::appUpdateInfo.isInitialized)
            throw RuntimeException(getString(R.string.no_update_available))
        appUpdateManager.startUpdateFlowForResult(
            appUpdateInfo,
            AppUpdateType.IMMEDIATE,
            this,
            IMMEDIATE_UPDATE_REQUEST_CODE
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMMEDIATE_UPDATE_REQUEST_CODE) {
            if (resultCode != RESULT_OK) {
                if (::inAppDelegate.isInitialized)
                    inAppDelegate.updateFailed()
                else
                    checkUpdate()
            }
        } else if (requestCode == FLEXIBLE_UPDATE_REQUEST_CODE) {
            if (resultCode != RESULT_OK) {
                if (::inAppDelegate.isInitialized)
                    inAppDelegate.updateFailed()
                else
                    Toast.makeText(this, getString(R.string.update_app_soon), Toast.LENGTH_SHORT)
                        .show()
            }
        }
    }
}