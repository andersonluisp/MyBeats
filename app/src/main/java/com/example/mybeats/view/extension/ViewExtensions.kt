package com.example.mybeats.view.extension

import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import com.bumptech.glide.Glide

fun View.visible() {
    this.isVisible = true
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun ImageView.loadUrl(view: View, url: String) {
    Glide.with(view)
        .load(url)
        .into(this)
}
