package com.lib.mylibrary.core.util

import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("bgColor")
fun setViewBackgroundColor(view: View, color: String){
    view.setBackgroundColor(Color.parseColor(color))
}

@BindingAdapter("textViewColor")
fun setTextColor(textView: TextView, color: String){
    textView.setTextColor(Color.parseColor(color))
}