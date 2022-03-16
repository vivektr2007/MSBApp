package xyz.teknol.utils.extensions

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.material.snackbar.Snackbar
import xyz.teknol.utils.R
import xyz.teknol.utils.callbacks.AlertDialogCallback
import xyz.teknol.utils.callbacks.ListDialogCallback


private var progressDialog: ProgressDialog? = null
private var loadingDialog: Dialog? = null

//fun showProgressDialog(context: Context, message: String?) {
//    if (!context.getParentActivity()?.isFinishing!! && !context.getParentActivity()?.isDestroyed!!) {
//        if (progressDialog == null) {
//            progressDialog = ProgressDialog(context)
//        }
//        progressDialog!!.setTitle("Please Wait")
//        progressDialog!!.setMessage(message)
//        progressDialog!!.setCancelable(false)
//        progressDialog!!.show()
//    }
//}
//
//fun hideProgressDialog(context: Context) {
//    if (!context.getParentActivity()?.isFinishing!! && !context.getParentActivity()?.isDestroyed!!) {
//        progressDialog?.dismiss()
//    }
//}


fun FragmentActivity.showProgressDialog(message: String) {
    if (!this.isFinishing && !this.isDestroyed) {
        if (progressDialog == null) {
            loadingDialog = AppCompatDialog(this, R.style.Theme_Dialog)
        }
        loadingDialog!!.setContentView(R.layout.loading_dialog)
        val txtMsg: TextView = loadingDialog!!.findViewById(R.id.txtMsg)
        txtMsg.text = message
        loadingDialog!!.setCancelable(false)
        loadingDialog!!.show()
    }
}


fun FragmentActivity.showProgressDialog() {
    if (!this.isFinishing && !this.isDestroyed) {
        if (progressDialog == null) {
            loadingDialog = AppCompatDialog(this, R.style.Theme_Dialog)
        }
        loadingDialog!!.setContentView(R.layout.loading_dialog)
        val txtMsg: TextView = loadingDialog!!.findViewById(R.id.txtMsg)
        txtMsg.text = getString(R.string.please_wait)
        loadingDialog!!.setCancelable(false)
        loadingDialog!!.show()
    }
}

fun FragmentActivity.hideProgressDialog() {
    if (!this.isFinishing && !this.isDestroyed) {
        loadingDialog?.dismiss()
    }
}


fun Fragment.showProgressDialog() {
    if (!requireActivity().isFinishing && !requireActivity().isDestroyed) {
        if (progressDialog == null) {
            loadingDialog = AppCompatDialog(requireContext(), R.style.Theme_Dialog)
        }
        loadingDialog!!.setContentView(R.layout.loading_dialog)
        val txtMsg: TextView = loadingDialog!!.findViewById(R.id.txtMsg)
        txtMsg.text = getString(R.string.please_wait)
        loadingDialog!!.setCancelable(false)
        loadingDialog!!.show()
    }
}

fun Fragment.showProgressDialog(message: String) {
    if (!requireActivity().isFinishing && !requireActivity().isDestroyed) {
        if (progressDialog == null) {
            loadingDialog = AppCompatDialog(requireContext(), R.style.Theme_Dialog)
        }
        loadingDialog!!.setContentView(R.layout.loading_dialog)
        val txtMsg: TextView = loadingDialog!!.findViewById(R.id.txtMsg)
        txtMsg.text = message
        loadingDialog!!.setCancelable(false)
        loadingDialog!!.show()
    }
}

fun Fragment.hideProgressDialog() {
    if (!requireActivity().isFinishing && !requireActivity().isDestroyed) {
        loadingDialog?.dismiss()
    }
}


fun FragmentActivity.showListDialog(
    list: List<String>,
    dialogId: Int,
    callback: ListDialogCallback
) {
    if (!this.isFinishing && !this.isDestroyed) {
        val dialogAdapter: ArrayAdapter<String> =
            ArrayAdapter(this, android.R.layout.select_dialog_singlechoice, list)
        val dialogBuilder =
            AlertDialog.Builder(this)
                .setAdapter(
                    dialogAdapter
                ) { _: DialogInterface?, which: Int ->
                    callback.onItemSelected(
                        which,
                        list[which],
                        dialogId
                    )
                }
        dialogBuilder.setTitle(null)
        val alertDialog = dialogBuilder.create()
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        alertDialog.setOnDismissListener {
            callback.onDismiss(
                dialogId
            )
        }
        alertDialog.show()
    }
}

fun FragmentActivity.showListDialog(
    list: List<String>,
    title: String,
    dialogId: Int,
    callback: ListDialogCallback
) {
    if (!this.isFinishing && !this.isDestroyed) {
        val dialogAdapter: ArrayAdapter<String> =
            ArrayAdapter(this, android.R.layout.select_dialog_singlechoice, list)
        val dialogBuilder =
            AlertDialog.Builder(this)
                .setAdapter(
                    dialogAdapter
                ) { _: DialogInterface?, which: Int ->
                    callback.onItemSelected(
                        which,
                        list[which],
                        dialogId
                    )
                }
        dialogBuilder.setTitle(null)
        val alertDialog = dialogBuilder.create()
        alertDialog.setTitle(title)
        alertDialog.setOnDismissListener {
            callback.onDismiss(
                dialogId
            )
        }
        alertDialog.show()
    }
}

fun FragmentActivity.showAlert(
    title: String,
    message: String,
    dialogId: Int,
    callback: AlertDialogCallback
) {
    if (!this.isFinishing && !this.isDestroyed) {
        AlertDialog.Builder(this)
            .setTitle(if (title.isNotEmpty()) title else null)
            .setMessage(if (message.isNotEmpty()) message else null)
            .setCancelable(false)
            .setPositiveButton(
                "ok"
            ) { _: DialogInterface?, _: Int ->
                callback.onPositiveButton(
                    dialogId
                )
            }
            .setNegativeButton(
                "Cancel"
            ) { _: DialogInterface?, _: Int ->
                callback.onNegativeButton(
                    dialogId
                )
            }
            .create()
            .show()
    }
}

fun FragmentActivity.showAlert(
    title: String,
    message: String,
    dialogId: Int,
    positiveButton: String?,
    callback: AlertDialogCallback
) {
    if (!this.isFinishing && !this.isDestroyed) {
        AlertDialog.Builder(this)
            .setTitle(if (title.isNotEmpty()) title else null)
            .setMessage(if (message.isNotEmpty()) message else null)
            .setCancelable(false)
            .setNegativeButton(
                positiveButton
            ) { _: DialogInterface?, _: Int ->
                callback.onPositiveButton(
                    dialogId
                )
            }
            .create()
            .show()
    }
}

fun FragmentActivity.showAlert(title: String, message: String) {
    if (!this.isFinishing && !this.isDestroyed) {
        AlertDialog.Builder(this)
            .setTitle(if (title.isNotEmpty()) title else null)
            .setMessage(if (message.isNotEmpty()) message else null)
            .setCancelable(false)
            .setPositiveButton("OK", null)
            .create()
            .show()
    }
}

fun View.showSnackBar(message: String?) {
    if (!context.getParentActivity()?.isFinishing!! && !context.getParentActivity()?.isDestroyed!!) {
        val snackbar = Snackbar.make(
            this,
            message!!,
            Snackbar.LENGTH_SHORT
        )
        snackbar.view.setBackgroundColor(resources.getColor(R.color.black))
        val textView = snackbar.view.findViewById<TextView>(R.id.snackbar_text)
        textView.setTextColor(resources.getColor(R.color.white))
        snackbar.show()
    }
}


fun FragmentActivity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

//fun getPreferences(context: Context): PreferenceManager {
//    return PreferenceManager.getInstance(context)
//}


