package xyz.teknol.utils.validator

import android.widget.EditText

data class ValidatorModel(
    val field: EditText,
    val validator: (String) -> Boolean,
    val errorMessage: String
)
