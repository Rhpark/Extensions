package kr.open.library.easy_extensions.context

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.Bundle
import kr.open.library.logcat.Logx

// Note: getColorCompat and getDrawableCompat have been moved to resource/ResourceExtensions.kt for better organization

/**
 * Starts an activity with optional extras and intent flags
 * Handles ActivityNotFoundException gracefully with logging
 * 
 * @param activity The target activity class
 * @param extras Optional bundle of extras to pass
 * @param intentFlags Optional array of intent flags to add
 * 
 * Example:
 * startActivity(ProfileActivity::class.java, bundleOf("user_id" to 123), intArrayOf(Intent.FLAG_ACTIVITY_NEW_TASK))
 */
public inline fun Context.startActivity(
    activity: Class<*>, 
    extras: Bundle? = null, 
    intentFlags: IntArray? = null
) {
    try {
        val intent = Intent(this, activity).apply {
            extras?.let { putExtras(it) }
            intentFlags?.forEach { flag -> addFlags(flag) }
        }
        startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        Logx.e("Activity not found: ${activity.simpleName}. Make sure it's declared in AndroidManifest.xml")
    }
}

/**
 * Safely starts an activity, returning true if successful, false otherwise
 * 
 * @param activity The target activity class
 * @param extras Optional bundle of extras to pass
 * @param intentFlags Optional array of intent flags to add
 * @return true if activity was started successfully, false otherwise
 * 
 * Example:
 * val success = startActivitySafely(ProfileActivity::class.java)
 * if (!success) showError("Cannot open profile")
 */
public inline fun Context.startActivitySafely(
    activity: Class<*>, 
    extras: Bundle? = null, 
    intentFlags: IntArray? = null
): Boolean {
    return try {
        val intent = Intent(this, activity).apply {
            extras?.let { putExtras(it) }
            intentFlags?.forEach { flag -> addFlags(flag) }
        }
        startActivity(intent)
        true
    } catch (e: ActivityNotFoundException) {
        Logx.e("Activity not found: ${activity.simpleName}")
        false
    } catch (e: SecurityException) {
        Logx.e("Security exception when starting activity: ${activity.simpleName}")
        false
    }
}