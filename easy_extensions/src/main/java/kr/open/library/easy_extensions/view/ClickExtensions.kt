package kr.open.library.easy_extensions.view

import android.view.View

/**
 * Debounce click listener utility to prevent multiple rapid clicks
 * 중복 클릭 방지를 위한 디바운스 클릭 리스너 유틸리티
 */

// 전역 디바운스 설정 - Global debounce settings
private const val DEFAULT_DEBOUNCE_INTERVAL = 300L // milliseconds
private var globalDebounceInterval = DEFAULT_DEBOUNCE_INTERVAL

/**
 * Sets the global debounce interval for all click listeners
 * 모든 클릭 리스너의 전역 디바운스 간격 설정
 *
 * @param intervalMillis The debounce interval in milliseconds
 */
public fun setGlobalClickDebounceInterval(intervalMillis: Long) {
    globalDebounceInterval = intervalMillis
}

/**
 * Gets the current global debounce interval
 * 현재 전역 디바운스 간격 조회
 *
 * @return The current debounce interval in milliseconds
 */
public fun getGlobalClickDebounceInterval(): Long = globalDebounceInterval

/**
 * Sets a click listener with debounce functionality using the global interval
 * 전역 간격을 사용하여 디바운스 기능이 있는 클릭 리스너 설정
 *
 * @param listener The click listener to execute
 *
 * Example:
 * ```
 * button.setOnClickListenerWithDebounce {
 *     // This will only execute once every 300ms (default)
 *     navigateToNextScreen()
 * }
 * ```
 */
public fun View.setOnClickListenerWithDebounce(listener: (View) -> Unit) {
    setOnClickListenerWithDebounce(globalDebounceInterval, listener)
}

/**
 * Sets a click listener with custom debounce interval
 * 사용자 정의 디바운스 간격으로 클릭 리스너 설정
 *
 * @param debounceInterval The debounce interval in milliseconds
 * @param listener The click listener to execute
 *
 * Example:
 * ```
 * submitButton.setOnClickListenerWithDebounce(1000L) {
 *     // This will only execute once every 1 second
 *     submitForm()
 * }
 * ```
 */
public fun View.setOnClickListenerWithDebounce(
    debounceInterval: Long,
    listener: (View) -> Unit,
) {
    var lastClickTime = 0L

    setOnClickListener { view ->
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastClickTime >= debounceInterval) {
            lastClickTime = currentTime
            listener(view)
        }
    }
}

/**
 * Extension function to check if a view can be clicked (debounce check)
 * 뷰가 클릭 가능한지 확인하는 확장 함수 (디바운스 체크)
 *
 * This is useful when you want to manually check debounce logic without setting a listener
 * 리스너 설정 없이 디바운스 로직을 수동으로 확인하고 싶을 때 유용합니다
 *
 * @param debounceInterval The debounce interval in milliseconds (default: global interval)
 * @return true if the view can be clicked, false if still in debounce period
 *
 * Example:
 * ```
 * if (button.canBeClicked()) {
 *     performAction()
 * }
 * ```
 */
public fun View.canBeClicked(debounceInterval: Long = globalDebounceInterval): Boolean {
    val lastClickTime = getTag(ViewIds.LAST_CLICK_TIME) as? Long ?: 0L
    val currentTime = System.currentTimeMillis()

    if (currentTime - lastClickTime >= debounceInterval) {
        setTag(ViewIds.LAST_CLICK_TIME, currentTime)
        return true
    }
    return false
}

/**
 * Performs a debounced action if the view can be clicked
 * 뷰가 클릭 가능한 경우에만 디바운스된 액션 수행
 *
 * @param debounceInterval The debounce interval in milliseconds (default: global interval)
 * @param action The action to perform
 *
 * Example:
 * ```
 * button.performDebouncedAction {
 *     // This action will be debounced
 *     handleButtonClick()
 * }
 * ```
 */
internal inline fun View.performDebouncedAction(
    debounceInterval: Long = globalDebounceInterval,
    action: () -> Unit,
) {
    if (canBeClicked(debounceInterval)) {
        action()
    }
}

/**
 * Multiple views debounce utility
 * 여러 뷰 디바운스 유틸리티
 *
 * Sets the same debounced click listener on multiple views
 * 여러 뷰에 동일한 디바운스 클릭 리스너 설정
 *
 * @param debounceInterval The debounce interval in milliseconds (default: global interval)
 * @param listener The click listener to execute
 *
 * Example:
 * ```
 * listOf(button1, button2, button3).setOnClickListenerWithDebounce { view ->
 *     when (view.id) {
 *         R.id.button1 -> handleButton1()
 *         R.id.button2 -> handleButton2()
 *         R.id.button3 -> handleButton3()
 *     }
 * }
 * ```
 */
public fun List<View>.setOnClickListenerWithDebounce(
    debounceInterval: Long = globalDebounceInterval,
    listener: (View) -> Unit,
) {
    forEach { view ->
        view.setOnClickListenerWithDebounce(debounceInterval, listener)
    }
}

/**
 * Reset debounce state for a view
 * 뷰의 디바운스 상태 리셋
 *
 * This is useful when you want to immediately allow the next click
 * 다음 클릭을 즉시 허용하고 싶을 때 유용합니다
 *
 * Example:
 * ```
 * button.resetDebounceState()
 * ```
 */
public fun View.resetDebounceState() {
    setTag(ViewIds.LAST_CLICK_TIME, 0L)
}
