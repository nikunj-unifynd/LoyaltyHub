package com.lib.mylibrary.core.util

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.util.TypedValue
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import kotlin.math.roundToInt


object Utils {
    fun dpToPx(context: Context, dp: Number): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), context.resources.displayMetrics).roundToInt();
    }

    fun startURLIntent(context: Context, url: String) {
        val intent = getURLIntent(url)
        context.startActivity(intent)
    }

    fun getURLIntent(url: String): Intent {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        return intent
    }

    @ColorInt
    fun getColorInt(context: Context, colorRes: Int): Int {
        return ContextCompat.getColor(context, colorRes)
    }

    @ColorInt
    fun getColorInt(colorHex: String): Int {
        return try {
            if(!colorHex.startsWith("#"))
                Color.parseColor("#$colorHex")
            else
                Color.parseColor(colorHex)
        } catch (e:Exception) {
            //Timber.e(e)
            Color.BLACK
        }
    }

}