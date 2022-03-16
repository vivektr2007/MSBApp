package com.msb.presentation.authorization.login.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import com.msb.R
import com.msb.databinding.CreateTotdDialogBinding
import xyz.teknol.utils.extensions.getString
import xyz.teknol.utils.validator.ValidatorBuilder


class CreateTotdDialog(context: Context, private val callback: Callback) :
    Dialog(context, R.style.Theme_Custom_Dialog) {
    //    private lateinit var fingerPrintManager: KFingerprintManager
    private lateinit var binding: CreateTotdDialogBinding
    private lateinit var validator: ValidatorBuilder
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(
                context
            ), R.layout.create_totd_dialog, null, false
        )

        setContentView(binding.root)
        //validator = ValidatorBuilder()
//            .addField(
//                binding.editTextTextWritePost,
//                { s -> s.lengthValidator(1) },
//                context.getString(R.string.please_enter_text)
//            )

        binding.tabLayout[0].setOnClickListener {
            binding.todMainLayout.visibility = View.VISIBLE
            binding.writePostMainLayout.visibility = View.GONE
        }
        binding.btnTodSubmit.setOnClickListener {
           // if (binding.editTextTextWritePost.text.length > 1) {
                callback.onCreateTod(binding.editTextTextWritePost.getString())
           // }

        }
//        binding.tabLayout[po].setOnClickListener {
//            binding.todMainLayout.visibility = View.GONE
//            binding.writePostMainLayout.visibility = View.VISIBLE
//        }


    }

    interface Callback {
        //        fun onCancelClick()
//        fun onFingerprintSuccess()
        fun onCreateTod(string: String)
    }
}