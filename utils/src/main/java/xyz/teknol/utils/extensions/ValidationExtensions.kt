package xyz.teknol.utils.extensions

import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.widget.EditText

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            afterTextChanged.invoke(s.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    })
}

fun EditText.validate(message: String, validator: (String) -> Boolean) {
    this.afterTextChanged {
        this.error = if (validator(it)) null else message
    }
    this.error = if (validator(this.text.toString())) null else message
}

fun String.emailValidator(): Boolean =
    this.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun String.editTextValidator(): Boolean =
    this.isNotEmpty()

fun String.phoneValidator(): Boolean =
    this.isNotEmpty() && Patterns.PHONE.matcher(this).matches()

fun String.lengthValidator(length: Int): Boolean =
    this.isNotEmpty() && this.length >= length

fun String.alphaNumericValidator(): Boolean {
    val numRegex = Regex(".*[0-9].*")
    val alphaRegex = Regex(".*[a-zA-Z].*")
    return this.matches(numRegex) && this.matches(alphaRegex)
}

fun String.alphaNumericSpecialCharacterValidator(): Boolean {
    val numRegex = Regex(".*[0-9].*")
    val alphaRegex = Regex(".*[a-zA-Z].*")
    val especialCharRegex = Regex("[!@#$%&*()_+=|<>?{}\\[\\]~-]")
    return this.matches(numRegex) && this.matches(alphaRegex) && this.matches(especialCharRegex)
}


