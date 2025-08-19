package kr.open.library.easy_extensions.context

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.core.content.ContextCompat
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

/**
 * Checks if a specific permission is granted
 * 
 * @param permission The permission to check
 * @return true if permission is granted, false otherwise
 * 
 * Example:
 * ```
 * if (hasPermission(Manifest.permission.CAMERA)) {
 *     openCamera()
 * }
 * ```
 */
public fun Context.hasPermission(permission: String): Boolean =
    ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED

/**
 * Opens the app's settings page in system settings
 * Useful for directing users to grant permissions manually
 * 
 * Example:
 * ```
 * openAppSettings()
 * ```
 */
public fun Context.openAppSettings() {
    try {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", packageName, null)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        Logx.e("Cannot open app settings")
    }
}

/**
 * Opens a web URL in the default browser
 * 
 * @param url The URL to open
 * @return true if successfully opened, false otherwise
 * 
 * Example:
 * ```
 * val success = openUrl("https://www.example.com")
 * ```
 */
public fun Context.openUrl(url: String): Boolean {
    return try {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        startActivity(intent)
        true
    } catch (e: ActivityNotFoundException) {
        Logx.e("No app found to handle URL: $url")
        false
    }
}

/**
 * Opens the dialer with a pre-filled phone number
 * 
 * @param phoneNumber The phone number to dial
 * @return true if successfully opened, false otherwise
 * 
 * Example:
 * ```
 * val success = dialPhoneNumber("+1-555-123-4567")
 * ```
 */
public fun Context.dialPhoneNumber(phoneNumber: String): Boolean {
    return try {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber")).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        startActivity(intent)
        true
    } catch (e: ActivityNotFoundException) {
        Logx.e("No dialer app found")
        false
    }
}

/**
 * Opens the default email app with pre-filled email
 * 
 * @param email The email address
 * @param subject Optional email subject
 * @param body Optional email body
 * @return true if successfully opened, false otherwise
 * 
 * Example:
 * ```
 * sendEmail("support@example.com", "Bug Report", "Description of the issue...")
 * ```
 */
public fun Context.sendEmail(
    email: String,
    subject: String? = null,
    body: String? = null
): Boolean {
    return try {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:$email")
            subject?.let { putExtra(Intent.EXTRA_SUBJECT, it) }
            body?.let { putExtra(Intent.EXTRA_TEXT, it) }
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        startActivity(intent)
        true
    } catch (e: ActivityNotFoundException) {
        Logx.e("No email app found")
        false
    }
}

/**
 * Shares text content using the system share dialog
 * 
 * @param text The text to share
 * @param subject Optional subject for the share
 * @param chooserTitle Optional title for the chooser dialog
 * @return true if successfully opened, false otherwise
 * 
 * Example:
 * ```
 * shareText("Check out this app!", subject = "App Recommendation")
 * ```
 */
public fun Context.shareText(
    text: String,
    subject: String? = null,
    chooserTitle: String = "Share via"
): Boolean {
    return try {
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, text)
            subject?.let { putExtra(Intent.EXTRA_SUBJECT, it) }
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        val chooser = Intent.createChooser(shareIntent, chooserTitle).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        startActivity(chooser)
        true
    } catch (e: ActivityNotFoundException) {
        Logx.e("No app found to handle sharing")
        false
    }
}

/**
 * Shows a toast message (convenience method)
 * 
 * @param message The message to display
 * @param duration Toast duration (default: Toast.LENGTH_SHORT)
 * 
 * Example:
 * ```
 * showToast("Operation completed successfully")
 * ```
 */
public fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

/**
 * Checks if an app with the given package name is installed
 * 
 * @param packageName The package name to check
 * @return true if app is installed, false otherwise
 * 
 * Example:
 * ```
 * if (isAppInstalled("com.whatsapp")) {
 *     // WhatsApp is installed
 * }
 * ```
 */
public fun Context.isAppInstalled(packageName: String): Boolean {
    return try {
        packageManager.getPackageInfo(packageName, 0)
        true
    } catch (e: PackageManager.NameNotFoundException) {
        false
    }
}