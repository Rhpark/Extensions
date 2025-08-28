package kr.open.library.easy_extensions.null_safety

/**
 * Null safety extensions for cleaner and safer null handling
 */

/**
 * Executes the given action if this value is not null and returns the original value
 * More idiomatic version using also() for side effects
 *
 * @param action The action to execute with the non-null value
 * @return The original value for chaining
 *
 * Example:
 * user.ifNotNull { updateProfile(it) }
 */
public inline fun <T> T?.ifNotNull(action: (T) -> Unit): T? = this?.also(action)

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

/**
 * Safely casts this object to the specified type T
 *
 * @return The object cast to type T, or null if casting fails
 *
 * Example:
 * val userProfile = response.safeCast<UserProfile>()
 * val errorResponse = response.safeCast<ErrorResponse>()
 */
public inline fun <reified T> Any?.safeCast(): T? = this as? T

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

/**
 * Returns this value if not null, otherwise returns the result of the default value function
 *
 * @param defaultValue Function that provides the default value
 * @return This value if not null, otherwise the result of defaultValue function
 *
 * Example:
 * val config = userSettings.orElse { getDefaultSettings() }
 */
public inline fun <T> T?.orElse(defaultValue: () -> T): T = this ?: defaultValue()

/**
 * Returns this value if it's not null and satisfies the predicate, otherwise null
 *
 * @param predicate The condition the value must satisfy
 * @return This value if not null and satisfies predicate, otherwise null
 *
 * Example:
 * val validEmail = email.takeIfNotNull { it.contains("@") }
 */
public inline fun <T> T?.takeIfNotNull(predicate: (T) -> Boolean): T? = this?.takeIf(predicate)
