package kr.open.library.easy_extensions.context

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.annotation.CheckResult
import androidx.core.content.ContextCompat
import kr.open.library.easy_extensions.result.SimpleResult

/**
 * Context Extensions with Result pattern for safe operation handling
 * 안전한 작업 처리를 위한 Result 패턴을 사용한 Context 확장 함수들
 */

/**
 * Safely starts an activity and returns a Result indicating success or failure
 * 안전하게 액티비티를 시작하고 성공 또는 실패를 나타내는 Result 반환
 *
 * @param activity The target activity class
 * @param extras Optional bundle of extras to pass
 * @param intentFlags Optional array of intent flags to add
 * @return SimpleResult.Success if activity was started successfully, SimpleResult.Failure otherwise
 *
 * Example:
 * ```
 * startActivityResult(ProfileActivity::class.java)
 *     .onSuccess {
 *         // Activity started successfully
 *     }
 *     .onFailure { exception ->
 *         // Handle error
 *         showError("Cannot open profile: ${exception.message}")
 *     }
 * ```
 */
@CheckResult
public inline fun Context.startActivityResult(
    activity: Class<*>,
    extras: Bundle? = null,
    intentFlags: IntArray? = null,
): SimpleResult<Unit> =
    SimpleResult.runCatching {
        val intent =
            Intent(this, activity).apply {
                extras?.let { putExtras(it) }
                // 안전 가드: Activity가 아닌 Context에서 호출 시 FLAG_ACTIVITY_NEW_TASK 자동 추가
                // Safety guard: Automatically add FLAG_ACTIVITY_NEW_TASK when called from non-Activity Context
                if (this@startActivityResult !is Activity) {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                intentFlags?.forEach { flag -> addFlags(flag) }
            }
        startActivity(intent)
    }

/**
 * Opens a URL and returns a Result indicating success or failure
 * URL을 열고 성공 또는 실패를 나타내는 Result 반환
 *
 * @param url The URL to open
 * @return SimpleResult.Success if URL was opened successfully, SimpleResult.Failure otherwise
 *
 * Example:
 * ```
 * openUrlResult("https://www.example.com")
 *     .onSuccess {
 *         showToast("Browser opened successfully")
 *     }
 *     .onFailure { exception ->
 *         showToast("Cannot open URL: ${exception.message}")
 *     }
 * ```
 */
@CheckResult
public fun Context.openUrlResult(url: String): SimpleResult<Unit> =
    SimpleResult.runCatching {
        val intent =
            Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
        startActivity(intent)
    }

/**
 * Opens the dialer with a pre-filled phone number and returns a Result
 * 미리 채워진 전화번호로 다이얼러를 열고 Result 반환
 *
 * @param phoneNumber The phone number to dial
 * @return SimpleResult.Success if dialer was opened successfully, SimpleResult.Failure otherwise
 *
 * Example:
 * ```
 * dialPhoneNumberResult("+1-555-123-4567")
 *     .onSuccess {
 *         showToast("Dialer opened")
 *     }
 *     .onFailure { exception ->
 *         showToast("Cannot open dialer: ${exception.message}")
 *     }
 * ```
 */
@CheckResult
public fun Context.dialPhoneNumberResult(phoneNumber: String): SimpleResult<Unit> =
    SimpleResult.runCatching {
        val intent =
            Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber")).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
        startActivity(intent)
    }

/**
 * Opens the default email app with pre-filled email and returns a Result
 * 미리 채워진 이메일로 기본 이메일 앱을 열고 Result 반환
 *
 * @param email The email address
 * @param subject Optional email subject
 * @param body Optional email body
 * @return SimpleResult.Success if email app was opened successfully, SimpleResult.Failure otherwise
 *
 * Example:
 * ```
 * sendEmailResult("support@example.com", "Bug Report", "Description...")
 *     .onSuccess {
 *         showToast("Email app opened")
 *     }
 *     .onFailure { exception ->
 *         showToast("Cannot open email app: ${exception.message}")
 *     }
 * ```
 */
@CheckResult
public fun Context.sendEmailResult(
    email: String,
    subject: String? = null,
    body: String? = null,
): SimpleResult<Unit> =
    SimpleResult.runCatching {
        val intent =
            Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:$email")
                subject?.let { putExtra(Intent.EXTRA_SUBJECT, it) }
                body?.let { putExtra(Intent.EXTRA_TEXT, it) }
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
        startActivity(intent)
    }

/**
 * Shares text content using the system share dialog and returns a Result
 * 시스템 공유 대화상자를 사용하여 텍스트 콘텐츠를 공유하고 Result 반환
 *
 * @param text The text to share
 * @param subject Optional subject for the share
 * @param chooserTitle Optional title for the chooser dialog
 * @return SimpleResult.Success if share dialog was opened successfully, SimpleResult.Failure otherwise
 *
 * Example:
 * ```
 * shareTextResult("Check out this app!", subject = "App Recommendation")
 *     .onSuccess {
 *         showToast("Share dialog opened")
 *     }
 *     .onFailure { exception ->
 *         showToast("Cannot share: ${exception.message}")
 *     }
 * ```
 */
@CheckResult
public fun Context.shareTextResult(
    text: String,
    subject: String? = null,
    chooserTitle: String = "Share via",
): SimpleResult<Unit> =
    SimpleResult.runCatching {
        val shareIntent =
            Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, text)
                subject?.let { putExtra(Intent.EXTRA_SUBJECT, it) }
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
        val chooser =
            Intent.createChooser(shareIntent, chooserTitle).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
        startActivity(chooser)
    }

/**
 * Checks if an app is installed and returns a Result with the installation status
 * 앱이 설치되어 있는지 확인하고 설치 상태와 함께 Result 반환
 *
 * @param packageName The package name to check
 * @return SimpleResult.Success(true) if app is installed, Result.Success(false) if not installed, SimpleResult.Failure on error
 *
 * Example:
 * ```
 * isAppInstalledResult("com.whatsapp")
 *     .onSuccess { isInstalled ->
 *         if (isInstalled) {
 *             showToast("WhatsApp is installed")
 *         } else {
 *             showToast("WhatsApp is not installed")
 *         }
 *     }
 *     .onFailure { exception ->
 *         showToast("Error checking app: ${exception.message}")
 *     }
 * ```
 */
@CheckResult
public fun Context.isAppInstalledResult(packageName: String): SimpleResult<Boolean> =
    SimpleResult.runCatching {
        try {
            packageManager.getPackageInfo(packageName, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

/**
 * Checks if a specific permission is granted and returns a Result
 * 특정 권한이 부여되어 있는지 확인하고 Result 반환
 *
 * @param permission The permission to check
 * @return SimpleResult.Success(true) if permission is granted, Result.Success(false) if not granted, SimpleResult.Failure on error
 *
 * Example:
 * ```
 * hasPermissionResult(Manifest.permission.CAMERA)
 *     .onSuccess { hasPermission ->
 *         if (hasPermission) {
 *             openCamera()
 *         } else {
 *             requestCameraPermission()
 *         }
 *     }
 *     .onFailure { exception ->
 *         showToast("Error checking permission: ${exception.message}")
 *     }
 * ```
 */
@CheckResult
public fun Context.hasPermissionResult(permission: String): SimpleResult<Boolean> =
    SimpleResult.runCatching {
        ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
    }

/**
 * Opens the app's settings page in system settings and returns a Result
 * 시스템 설정에서 앱의 설정 페이지를 열고 Result 반환
 *
 * @return SimpleResult.Success if settings page was opened successfully, SimpleResult.Failure otherwise
 *
 * Example:
 * ```
 * openAppSettingsResult()
 *     .onSuccess {
 *         showToast("Settings opened")
 *     }
 *     .onFailure { exception ->
 *         showToast("Cannot open settings: ${exception.message}")
 *     }
 * ```
 */
@CheckResult
public fun Context.openAppSettingsResult(): SimpleResult<Unit> =
    SimpleResult.runCatching {
        val intent =
            Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                data = Uri.fromParts("package", packageName, null)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
        startActivity(intent)
    }
