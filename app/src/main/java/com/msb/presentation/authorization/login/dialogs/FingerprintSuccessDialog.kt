package com.msb.presentation.authorization.login.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Window
import androidx.databinding.DataBindingUtil
import com.msb.R
import com.msb.databinding.FingerPrintSuccessDialogBinding


class FingerprintSuccessDialog(context: Context) : Dialog(context, R.style.Theme_Custom_Dialog) {
    private lateinit var binding: FingerPrintSuccessDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(
                context
            ), R.layout.finger_print_success_dialog, null, false
        )
        setContentView(binding.root)
    }
}