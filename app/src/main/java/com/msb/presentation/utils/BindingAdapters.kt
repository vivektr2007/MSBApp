package xyz.teknol.preto3.presentation.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.msb.framework.GlideApp

@BindingAdapter("url")
fun setUrl(view: ImageView, url: String) {
    GlideApp.with(view).load(url).into(view)
}

@BindingAdapter("circleUrl")
fun setCircleUrl(view: ImageView, url: String) {
    GlideApp.with(view).load(url).circleCrop().into(view)
}