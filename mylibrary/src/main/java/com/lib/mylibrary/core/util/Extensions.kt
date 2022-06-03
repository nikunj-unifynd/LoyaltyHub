package com.lib.mylibrary.core.util

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.graphics.*
import android.graphics.drawable.*
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

fun Context.showToast(message: String, time: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, time).show()
}

fun View.showView() {
    this.visibility = View.VISIBLE
}

fun View.hideView() {
    this.visibility = View.GONE
}

fun View.isVisible(): Boolean {
    return this.visibility == View.VISIBLE
}

fun getGradientDrawable(
    colorStart: Int,
    colorEnd: Int,
    orientation: GradientDrawable.Orientation,
    radius: Int,
    myContext: Context,
): GradientDrawable {
    val gradientDrawable = GradientDrawable(orientation, intArrayOf(colorEnd, colorStart))
    gradientDrawable.cornerRadius = Utils.dpToPx(myContext, radius).toFloat()
    return gradientDrawable
}


fun View.setCustomDrawableWithColor(@ColorInt color: Int) {
    when (val tagBackground = this.background?.mutate()) {
        is ShapeDrawable -> {
            tagBackground.paint.color = color
        }
        is GradientDrawable -> {
            tagBackground.setColor(color)
        }
        is ColorDrawable -> {
            tagBackground.color = color
        }
    }
}

fun TextView.setDrawableTintColor(drawablePosition: Int, color: Int) {
    this.compoundDrawablesRelative.getOrNull(drawablePosition)?.mutate()?.setTint(color)
}

/**
 * In some cases {@link com.unifynd.lite.frt.core.views.utils.Extensions#setDrawableTintColor()}
 * does not work as 'compoundDrawablesRelative' returns empty array
 */
fun TextView.setDrawableTintColorAlt(drawablePosition: Int, color: Int) {
    this.compoundDrawables.getOrNull(drawablePosition)?.mutate()?.setTint(color)
}


fun getDateOfFormat(calendar: Calendar, pattern: String): String {
    val sdf = SimpleDateFormat(pattern, Locale.ENGLISH)
    return sdf.format(calendar.time)
}


fun getCalendarFromString(dateString: String, pattern: String): Calendar {
    val calendar = Calendar.getInstance()
    val sdf = SimpleDateFormat(pattern, Locale.ENGLISH)
    calendar.time = sdf.parse(dateString)!!
    return calendar
}

fun getCalendarFromStringWithCurrentDate(dateString: String, pattern: String): Calendar{
    val calendar = Calendar.getInstance()
    val tempCalendar = Calendar.getInstance()
    val sdf = SimpleDateFormat(pattern, Locale.ENGLISH)
    tempCalendar.time = sdf.parse(dateString)!!
    calendar.set(calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DATE),
        tempCalendar.get(Calendar.HOUR_OF_DAY),
        tempCalendar.get(Calendar.MINUTE),
        tempCalendar.get(
            Calendar.SECOND))
    return calendar
}

fun hideSoftKeyboard(activity: Activity) {
    val inputMethodManager: InputMethodManager = activity.getSystemService(
        Activity.INPUT_METHOD_SERVICE
    ) as InputMethodManager
    val view = activity.currentFocus
    if (view != null) {
        inputMethodManager.hideSoftInputFromWindow(
            view.windowToken, 0
        )
    }
}

fun openMallChangeBottomSheet(context: Context, activity: Activity){

}

fun Context.getPackageNameBasedOnVariant(isChennaiMall: Boolean = false): String {
    return if (isChennaiMall)
        "com.phoenix.nhance"
    else
        this.packageName
}


/**
 * Return true if this [Context] is available.
 * Availability is defined as the following:
 * + [Context] is not null
 * + [Context] is not destroyed (tested with [FragmentActivity.isDestroyed] or [Activity.isDestroyed])
 */
fun Context?.isAvailable(): Boolean {
    if (this == null) {
        return false
    } else if (this !is Application) {
        if (this is FragmentActivity) {
            return !this.isDestroyed
        } else if (this is Activity) {
            return !this.isDestroyed
        }
    }
    return true
}

@SuppressLint("SimpleDateFormat")
@Throws(IOException::class)
fun Context.createFile(isTemporary: Boolean = true, extension: String): File {
    // Create an image file name
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    var imageFileName = "FILE_$timeStamp"
    if (isTemporary) {
        imageFileName = "TMP_FILE"
    }
    val storageDir = cacheDir
    val image = File(storageDir, "$imageFileName.$extension")
    Log.i("********image", image.absolutePath)
    return image
}

fun Bitmap.toFile(context: Context, storeExternal: Boolean = false) : File? {
    val timeStamp = System.currentTimeMillis()/1000
    val filesDir: File
    var imageFile: File? = null
    val os: OutputStream
    try {
        filesDir = if (storeExternal) context.externalCacheDir ?: context.cacheDir else context.cacheDir
        imageFile = File(filesDir, "$timeStamp.jpg")
        os = FileOutputStream(imageFile)
        this.compress(Bitmap.CompressFormat.JPEG, 100, os)
        os.flush()
        os.close()
    } catch (e: Exception) {
        //Timber.e(e, "Error writing bitmap")
    }
    return imageFile
}


fun Context.areNotificationsEnabled(): Boolean {
    return NotificationManagerCompat.from(this).areNotificationsEnabled()
}

fun View.scaleView(fromY: Float, toY: Float, fromX: Float, toX: Float) {
    val anim = ScaleAnimation(
        fromX, toX,  // Start and end values for the X axis scaling
        fromY, toY,  // Start and end values for the Y axis scaling
        Animation.RELATIVE_TO_SELF, 0.5f,  // Pivot point of X scaling
        Animation.RELATIVE_TO_SELF, 0.5f) // Pivot point of Y scaling
    anim.fillAfter = true // Needed to keep the result of the animation
    anim.duration = 300
    this.startAnimation(anim)
}

fun isHoli(): Boolean {
    val start = Calendar.getInstance()
    start.set(Calendar.DAY_OF_MONTH, 25)
    start.set(Calendar.MONTH, Calendar.MARCH)
    start.set(Calendar.YEAR, 2021)
    start.set(Calendar.HOUR_OF_DAY, 0)
    start.set(Calendar.MINUTE, 0)
    start.set(Calendar.SECOND, 0)
    val endDate = Calendar.getInstance()
    endDate.set(Calendar.DAY_OF_MONTH, 30)
    endDate.set(Calendar.MONTH, Calendar.MARCH)
    endDate.set(Calendar.YEAR, 2021)
    endDate.set(Calendar.HOUR_OF_DAY, 0)
    endDate.set(Calendar.MINUTE, 0)
    endDate.set(Calendar.SECOND, 0)
    return Calendar.getInstance().time.after(start.time) && Calendar.getInstance().time.before(endDate.time)
}


fun Number?.toBoolean(): Boolean {
    if (this == null) return false
    return this.toInt() >= 1
}

fun Boolean?.toInt(): Int {
    if (this == null) return 0
    return if(this) 1 else 0
}

fun RecyclerView?.getCurrentPosition() : Int {
    return (this?.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
}

class FlowObserver<T> (
    lifecycleOwner: LifecycleOwner,
    private val flow: Flow<T>,
    private val collector: suspend (T) -> Unit
) {

    private var job: Job? = null

    init {
        lifecycleOwner.lifecycle.addObserver(LifecycleEventObserver {
                source: LifecycleOwner, event: Lifecycle.Event ->
            when (event) {
                Lifecycle.Event.ON_START -> {
                    job = source.lifecycleScope.launch {
                        flow.collect {
                            collector(it) }
                    }
                }
                Lifecycle.Event.ON_STOP -> {
                    job?.cancel()
                    job = null
                }
                else -> { }
            }
        })
    }
}
