package xyz.teknol.utils.validator

import android.widget.EditText
import com.google.android.material.textfield.TextInputLayout
import xyz.teknol.utils.extensions.getString

class ValidatorBuilder {

    private val validators = ArrayList<ValidatorModel>()

    fun addField(
        field: EditText,
        validator: (String) -> Boolean,
        errorMessage: String
    ): ValidatorBuilder {
        validators.add(ValidatorModel(field, validator, errorMessage))
        return this
    }

    fun addField(
        field: TextInputLayout,
        validator: (String) -> Boolean,
        errorMessage: String
    ): ValidatorBuilder {
        field.editText?.let { validators.add(ValidatorModel(it, validator, errorMessage)) }
        return this
    }

    fun validate(): Boolean {
        var isValidated = true
        for (i in validators.indices) {
            if (!validators[i].validator(validators[i].field.getString())) {
                validators[i].field.error = validators[i].errorMessage
                validators[i].field.requestFocus()
                isValidated = false
                break
            } else {
                validators[i].field.error = null
            }
        }
        return isValidated
    }

}