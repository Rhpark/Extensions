package kr.open.library.easy_extensions.string

import androidx.annotation.CheckResult
import kr.open.library.easy_extensions.result.SimpleResult
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * String Extensions with Result pattern for safe operation handling
 * 안전한 작업 처리를 위한 Result 패턴을 사용한 String 확장 함수들
 */

/**
 * Validates email format and returns a Result with the validation result
 * 이메일 형식을 검증하고 검증 결과와 함께 Result 반환
 *
 * @return SimpleResult.Success(true) if valid email, Result.Success(false) if invalid, SimpleResult.Failure on error
 *
 * Example:
 * ```
 * "user@example.com".validateEmailResult()
 *     .onSuccess { isValid ->
 *         if (isValid) {
 *             processEmail(this)
 *         } else {
 *             showError("Invalid email format")
 *         }
 *     }
 *     .onFailure { exception ->
 *         showError("Email validation error: ${exception.message}")
 *     }
 * ```
 */
@CheckResult
public fun String.validateEmailResult(): SimpleResult<Boolean> =
    SimpleResult.runCatching {
        android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }

/**
 * Validates phone number format and returns a Result with the validation result
 * 전화번호 형식을 검증하고 검증 결과와 함께 Result 반환
 *
 * @return SimpleResult.Success(true) if valid phone number, Result.Success(false) if invalid, SimpleResult.Failure on error
 *
 * Example:
 * ```
 * "+1-555-123-4567".validatePhoneNumberResult()
 *     .onSuccess { isValid ->
 *         if (isValid) {
 *             processPhoneNumber(this)
 *         } else {
 *             showError("Invalid phone number format")
 *         }
 *     }
 *     .onFailure { exception ->
 *         showError("Phone validation error: ${exception.message}")
 *     }
 * ```
 */
@CheckResult
public fun String.validatePhoneNumberResult(): SimpleResult<Boolean> =
    SimpleResult.runCatching {
        android.util.Patterns.PHONE.matcher(this).matches()
    }

/**
 * Validates URL format and returns a Result with the validation result
 * URL 형식을 검증하고 검증 결과와 함께 Result 반환
 *
 * @return SimpleResult.Success(true) if valid URL, Result.Success(false) if invalid, SimpleResult.Failure on error
 *
 * Example:
 * ```
 * "https://www.example.com".validateUrlResult()
 *     .onSuccess { isValid ->
 *         if (isValid) {
 *             openUrl(this)
 *         } else {
 *             showError("Invalid URL format")
 *         }
 *     }
 *     .onFailure { exception ->
 *         showError("URL validation error: ${exception.message}")
 *     }
 * ```
 */
@CheckResult
public fun String.validateUrlResult(): SimpleResult<Boolean> =
    SimpleResult.runCatching {
        android.util.Patterns.WEB_URL.matcher(this).matches()
    }

// REMOVED: toIntResult - Use stdlib's toIntOrNull() instead
// 제거됨: toIntResult - stdlib의 toIntOrNull() 사용 권장

// REMOVED: toDoubleResult - Use stdlib's toDoubleOrNull() instead
// 제거됨: toDoubleResult - stdlib의 toDoubleOrNull() 사용 권장

// REMOVED: toFloatResult - Use stdlib's toFloatOrNull() instead
// 제거됨: toFloatResult - stdlib의 toFloatOrNull() 사용 권장

// REMOVED: toLongResult - Use stdlib's toLongOrNull() instead
// 제거됨: toLongResult - stdlib의 toLongOrNull() 사용 권장

/**
 * Safely parses date string with given pattern and returns a Result
 * 주어진 패턴으로 날짜 문자열을 안전하게 파싱하고 Result 반환
 *
 * @param pattern The date pattern to use for parsing
 * @param locale The locale to use for parsing (default: Locale.getDefault())
 * @return SimpleResult.Success(Date) if parsing successful, SimpleResult.Failure on error
 *
 * Example:
 * ```
 * "2023-12-25".parseDateResult("yyyy-MM-dd")
 *     .onSuccess { date ->
 *         processDate(date)
 *     }
 *     .onFailure { exception ->
 *         showError("Invalid date format: ${exception.message}")
 *     }
 * ```
 */
@CheckResult
public fun String.parseDateResult(
    pattern: String,
    locale: Locale = Locale.getDefault(),
): SimpleResult<Date> =
    SimpleResult.runCatching {
        SimpleDateFormat(pattern, locale).parse(this)
            ?: throw ParseException("Failed to parse date: $this", 0)
    }

/**
 * Safely removes HTML tags and returns a Result with cleaned text
 * HTML 태그를 안전하게 제거하고 정리된 텍스트와 함께 Result 반환
 *
 * @return SimpleResult.Success(String) with cleaned text, SimpleResult.Failure on error
 *
 * Example:
 * ```
 * "<p>Hello <b>World</b></p>".stripHtmlTagsResult()
 *     .onSuccess { cleanText ->
 *         displayText(cleanText) // "Hello World"
 *     }
 *     .onFailure { exception ->
 *         showError("HTML processing error: ${exception.message}")
 *     }
 * ```
 */
@CheckResult
public fun String.stripHtmlTagsResult(): SimpleResult<String> =
    SimpleResult.runCatching {
        this.replace("<[^>]*>".toRegex(), "")
    }

/**
 * Safely removes whitespace and returns a Result with cleaned text
 * 공백을 안전하게 제거하고 정리된 텍스트와 함께 Result 반환
 *
 * @return SimpleResult.Success(String) with whitespace removed, SimpleResult.Failure on error
 *
 * Example:
 * ```
 * "  Hello   World  ".removeWhitespaceResult()
 *     .onSuccess { cleanText ->
 *         displayText(cleanText) // "HelloWorld"
 *     }
 *     .onFailure { exception ->
 *         showError("Text processing error: ${exception.message}")
 *     }
 * ```
 */
@CheckResult
public fun String.removeWhitespaceResult(): SimpleResult<String> =
    SimpleResult.runCatching {
        this.replace("\\s".toRegex(), "")
    }

/**
 * Safely capitalizes the first letter and returns a Result
 * 첫 글자를 안전하게 대문자로 만들고 Result 반환
 *
 * @return SimpleResult.Success(String) with first letter capitalized, SimpleResult.Failure on error
 *
 * Example:
 * ```
 * "hello world".capitalizeFirstLetterResult()
 *     .onSuccess { capitalizedText ->
 *         displayText(capitalizedText) // "Hello world"
 *     }
 *     .onFailure { exception ->
 *         showError("Capitalization error: ${exception.message}")
 *     }
 * ```
 */
@CheckResult
public fun String.capitalizeFirstLetterResult(): SimpleResult<String> =
    SimpleResult.runCatching {
        if (this.isEmpty()) {
            this
        } else {
            this.substring(0, 1).uppercase() + this.substring(1)
        }
    }

/**
 * Safely truncates string to specified length and returns a Result
 * 문자열을 지정된 길이로 안전하게 자르고 Result 반환
 *
 * @param maxLength Maximum length of the result string
 * @param suffix Suffix to append when truncated (default: "...")
 * @return SimpleResult.Success(String) with truncated text, SimpleResult.Failure on error
 *
 * Example:
 * ```
 * "This is a very long text".truncateResult(10)
 *     .onSuccess { truncatedText ->
 *         displayText(truncatedText) // "This is a..."
 *     }
 *     .onFailure { exception ->
 *         showError("Truncation error: ${exception.message}")
 *     }
 * ```
 */
@CheckResult
public fun String.truncateResult(
    maxLength: Int,
    suffix: String = "...",
): SimpleResult<String> =
    SimpleResult.runCatching {
        require(maxLength >= 0) { "maxLength must be non-negative" }

        if (this.length <= maxLength) {
            this
        } else {
            val truncatedLength = (maxLength - suffix.length).coerceAtLeast(0)
            this.substring(0, truncatedLength) + suffix
        }
    }

/**
 * Safely extracts numbers from string and returns a Result
 * 문자열에서 숫자를 안전하게 추출하고 Result 반환
 *
 * @return SimpleResult.Success(List<Int>) with extracted numbers, SimpleResult.Failure on error
 *
 * Example:
 * ```
 * "Price: $29.99, Quantity: 5 items".extractNumbersResult()
 *     .onSuccess { numbers ->
 *         processNumbers(numbers) // [29, 99, 5]
 *     }
 *     .onFailure { exception ->
 *         showError("Number extraction error: ${exception.message}")
 *     }
 * ```
 */
@CheckResult
public fun String.extractNumbersResult(): SimpleResult<List<Int>> =
    SimpleResult.runCatching {
        "\\d+".toRegex()
            .findAll(this)
            .map { it.value.toInt() }
            .toList()
    }

/**
 * Safely checks if string matches pattern and returns a Result
 * 문자열이 패턴과 일치하는지 안전하게 확인하고 Result 반환
 *
 * @param pattern Regular expression pattern to match
 * @return SimpleResult.Success(Boolean) with match result, SimpleResult.Failure on error
 *
 * Example:
 * ```
 * "abc123".matchesPatternResult("^[a-z]+\\d+$")
 *     .onSuccess { matches ->
 *         if (matches) {
 *             processValidInput(this)
 *         } else {
 *             showError("Input format is invalid")
 *         }
 *     }
 *     .onFailure { exception ->
 *         showError("Pattern matching error: ${exception.message}")
 *     }
 * ```
 */
@CheckResult
public fun String.matchesPatternResult(pattern: String): SimpleResult<Boolean> =
    SimpleResult.runCatching {
        this.matches(pattern.toRegex())
    }
