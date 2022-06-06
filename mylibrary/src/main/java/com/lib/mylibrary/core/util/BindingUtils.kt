package com.lib.mylibrary.core.util

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.material.card.MaterialCardView

@BindingAdapter("bgColor")
fun setViewBackgroundColor(view: View, color: String){
    view.setBackgroundColor(Color.parseColor(color))
}

@BindingAdapter("textViewColor")
fun setTextColor(textView: TextView, color: String){
    textView.setTextColor(Color.parseColor(color))
}

@BindingAdapter("cardBgColor")
fun setCardBackgroundColor(card: MaterialCardView, color: String){
    card.setCardBackgroundColor(Color.parseColor(color))
}

@BindingAdapter("cardBorderColor")
fun setCardBorderColor(card: MaterialCardView, color: String){
    card.strokeColor = Color.parseColor(color)
}

@BindingAdapter("imgSrc")
fun setImageResourceFromRemote(imageView: ImageView, resString: Any){
    if(resString is Int) imageView.setImageResource(resString)
    else if (resString is String) {
        Glide.with(imageView.context)
            .load(resString)
            .into(imageView)
    }
}