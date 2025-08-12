package kr.open.library.easy_extensions.context

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.PermissionInfo
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.provider.Settings
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat

public fun Context.getColorCompat(@ColorRes colorRes: Int): Int {
    return ContextCompat.getColor(this, colorRes)
}

public fun Context.getDrawableCompat(@DrawableRes drawableRes: Int): Drawable? {
    return ContextCompat.getDrawable(this, drawableRes)
}

public inline fun Context.startActivity(activity: Class<*>, extras: Bundle? = null, intentFlags: IntArray? = null) {
    val intent = Intent(this, activity).apply {
        extras?.let { putExtras(it) }
        intentFlags?.let { it.forEach { item->addFlags(item) } }
    }
    startActivity(intent)
}