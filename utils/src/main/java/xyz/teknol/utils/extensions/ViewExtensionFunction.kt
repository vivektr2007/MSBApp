package xyz.teknol.utils.extensions

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.google.android.material.textfield.TextInputLayout

// Get Activity Context from Any View
fun View.getParentActivity(): AppCompatActivity? {
    var context = this.context
    while (context is ContextWrapper) {
        if (context is AppCompatActivity) {
            return context
        }
        context = context.baseContext
    }
    return null
}

// Get Activity Context from Any View
fun Context.getParentActivity(): AppCompatActivity? {
    var context = this
    while (context is ContextWrapper) {
        if (context is AppCompatActivity) {
            return context
        }
        context = context.baseContext
    }
    return null
}

// Do Back from Any View
fun View.doBack() {
    if (this.context is Activity) {
        val activity = this.context as Activity
        activity.onBackPressed()
    }
}

fun Fragment.doBack() {
    requireActivity().onBackPressed()
}


// To clear Livedata Values
fun MutableLiveData<String>.doClear() {
    this.value = ""
}

// Visibility
fun View.doGone() {
    visibility = View.GONE
}

// Visibility
fun View.doInvisible() {
    visibility = View.INVISIBLE
}

fun View.doVisible() {
    visibility = View.VISIBLE
}

// To Show Toast
fun AppCompatActivity.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

// To Show Toast
fun showToast(message: String, context: Context) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun Fragment.showToast(message: String) {
    Toast.makeText(this.activity, message, Toast.LENGTH_SHORT).show()
}

fun Fragment.showSnackBar(message: String) {
    Toast.makeText(this.activity, message, Toast.LENGTH_SHORT).show()
}

// To Get String from View
fun EditText.getString(): String {
    return this.text.toString().trim()
}

// To Get String from View
fun TextInputLayout.getString(): String {
    return this.editText?.text.toString().trim()
}

// To Get String from View
fun TextInputLayout.showError(error: String) {
    this.editText?.error = error
}

fun TextView.getString(): String {
    return this.text.toString().trim()
}

fun String.getString(): String {
    return this.trim()
}