package kr.open.library.easy_extensions.result

import androidx.annotation.CheckResult

/**
 * Simple Result wrapper class for safe operation handling
 * 안전한 작업 처리를 위한 간단한 Result 래퍼 클래스
 */
public sealed class SimpleResult<T> {
    public data class Success<T>(val value: T) : SimpleResult<T>()

    public data class Failure<T>(val exception: Throwable) : SimpleResult<T>()

    @get:CheckResult
    public val isSuccess: Boolean get() = this is Success

    @get:CheckResult
    public val isFailure: Boolean get() = this is Failure

    @CheckResult
    public fun getOrNull(): T? =
        when (this) {
            is Success -> value
            is Failure -> null
        }

    @CheckResult
    public fun getOrDefault(defaultValue: T): T =
        when (this) {
            is Success -> value
            is Failure -> defaultValue
        }

    @CheckResult
    public inline fun getOrElse(onFailure: (exception: Throwable) -> T): T =
        when (this) {
            is Success -> value
            is Failure -> onFailure(exception)
        }

    companion object {
        @CheckResult
        public fun <T> success(value: T): SimpleResult<T> = Success(value)

        @CheckResult
        public fun <T> failure(exception: Throwable): SimpleResult<T> = Failure(exception)

        @CheckResult
        public inline fun <T> runCatching(block: () -> T): SimpleResult<T> =
            try {
                success(block())
            } catch (e: Exception) {
                failure(e)
            }
    }
}

@CheckResult
public inline fun <T> SimpleResult<T>.onSuccess(action: (value: T) -> Unit): SimpleResult<T> {
    if (this is SimpleResult.Success) {
        action(value)
    }
    return this
}

@CheckResult
public inline fun <T> SimpleResult<T>.onFailure(action: (exception: Throwable) -> Unit): SimpleResult<T> {
    if (this is SimpleResult.Failure) {
        action(exception)
    }
    return this
}

@CheckResult
public inline fun <T, R> SimpleResult<T>.map(transform: (value: T) -> R): SimpleResult<R> =
    when (this) {
        is SimpleResult.Success -> SimpleResult.runCatching { transform(value) }
        is SimpleResult.Failure -> SimpleResult.failure(exception)
    }

@CheckResult
public inline fun <T, R> SimpleResult<T>.fold(
    onSuccess: (value: T) -> R,
    onFailure: (exception: Throwable) -> R,
): R =
    when (this) {
        is SimpleResult.Success -> onSuccess(value)
        is SimpleResult.Failure -> onFailure(exception)
    }
