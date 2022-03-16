package com.msb.presentation.authorization.login.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Window
import androidx.databinding.DataBindingUtil
import com.msb.R
import com.msb.databinding.FingerPrintDialogBinding


class FingerPrintDialog(context: Context, private val callback: Callback) :
    Dialog(context, R.style.Theme_Custom_Dialog) {
    //    private lateinit var fingerPrintManager: KFingerprintManager
    private lateinit var binding: FingerPrintDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(
                context
            ), R.layout.finger_print_dialog, null, false
        )
        setContentView(binding.root)
        binding.imageView.setOnClickListener {
            callback.onFingerprintSuccess()
        }
        binding.button.setOnClickListener {
            callback.onCancelClick()
        }
//        fingerPrintManager = KFingerprintManager(context, key);
//        fingerPrintManager.authenticate(object : KFingerprintManager.AuthenticationCallback {
//            override fun onAuthenticationFailedWithHelp(help: String?) {
//                // Logic when decryption failed with a message
//            }
//
//            override fun onAuthenticationSuccess() {
//                // Logic when authentication has been successful
//            }
//
//            override fun onSuccessWithManualPassword(password: String) {
//                // Logic when authentication has been successful writting password manually
//            }
//
//            override fun onFingerprintNotRecognized() {
//                // Logic when fingerprint was not recognized
//            }
//
//            override fun onFingerprintNotAvailable() {
//                // Logic when fingerprint is not available
//            }
//
//            override fun onCancelled() {
//                // Logic when operation is cancelled by user
//            }
//        }, getSupportFragmentManager());

    }

    interface Callback {
        fun onCancelClick()
        fun onFingerprintSuccess()
    }
}