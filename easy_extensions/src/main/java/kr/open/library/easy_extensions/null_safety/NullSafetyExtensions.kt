package kr.open.library.easy_extensions.null_safety

/**
 * Null safety extensions for cleaner and safer null handling
 */

// REMOVED: ifNotNull - Use stdlib's ?.also { ... } instead
// 제거됨: ifNotNull - stdlib의 ?.also { ... } 사용 권장

/**
 * Executes the given action if this value is null and returns the original value
 *
 * @param action The action to execute when value is null
 * @return The original value for chaining
 *
 * Example:
 * profileImage.ifNull { showDefaultAvatar() }
 */
public inline fun <T> T?.ifNull(action: () -> Unit): T? {
    if (this == null) action()
    return this
}

// REMOVED: safeCast - Use stdlib's as? operator instead
// 제거됨: safeCast - stdlib의 as? 연산자 사용 권장

/**
 * Returns the first non-null value from the provided values
 * More efficient implementation that stops at first non-null
 *
 * @param values Variable number of nullable values to check
 * @return The first non-null value, or null if all are null
 *
 * Example:
 * val result = firstNotNull(userInput, cachedValue, defaultValue)
 */
public fun <T> firstNotNull(vararg values: T?): T? {
    for (value in values) {
        if (value != null) return value
    }
    return null
}

/**
 * Executes the given action with the non-null value, otherwise executes the null action
 *
 * @param notNullAction The action to execute when value is not null
 * @param nullAction The action to execute when value is null
 * @return The original value for chaining
 *
 * Example:
 * user.ifNotNullOrElse(
 *     notNullAction = { showUserProfile(it) },
 *     nullAction = { showLoginScreen() }
 * )
 */
public inline fun <T> T?.ifNotNullOrElse(
    notNullAction: (T) -> Unit,
    nullAction: () -> Unit,
): T? {
    if (this != null) {
        notNullAction(this)
    } else {
        nullAction()
    }
    return this
}

// REMOVED: orElse - Use stdlib's ?: (elvis) operator instead
// 제거됨: orElse - stdlib의 ?: (elvis) 연산자 사용 권장

// REMOVED: takeIfNotNull - Use stdlib's ?.takeIf { ... } instead
// 제거됨: takeIfNotNull - stdlib의 ?.takeIf { ... } 사용 권장
