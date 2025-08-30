package kr.open.library.easy_extensions

import kr.open.library.easy_extensions.null_safety.*
import org.junit.Assert.*
import org.junit.Test

/**
 * Unit tests for NullSafetyExtensions
 */
class NullSafetyExtensionsTest {
    // REMOVED: Tests for ifNotNull - Use stdlib's ?.also { ... } instead
    // 제거됨: ifNotNull 테스트 - stdlib의 ?.also { ... } 사용 권장
    
    @Test
    fun `stdlib also should work as ifNotNull replacement`() {
        val value = "test"
        var actionExecuted = false

        value?.also {
            actionExecuted = true
            assertEquals("test", it)
        }

        assertTrue(actionExecuted)
    }

    @Test
    fun `ifNull should execute action when value is null`() {
        val value: String? = null
        var actionExecuted = false

        value.ifNull {
            actionExecuted = true
        }

        assertTrue(actionExecuted)
    }

    @Test
    fun `ifNull should not execute action when value is not null`() {
        val value: String? = "test"
        var actionExecuted = false

        value.ifNull {
            actionExecuted = true
        }

        assertFalse(actionExecuted)
    }

    // REMOVED: Tests for safeCast - Use stdlib's as? operator instead
    // 제거됨: safeCast 테스트 - stdlib의 as? 연산자 사용 권장
    
    @Test
    fun `stdlib as operator should work as safeCast replacement`() {
        val value: Any = "test string"
        val result = value as? String
        assertEquals("test string", result)
        
        val invalidValue: Any = 123
        val invalidResult = invalidValue as? String
        assertNull(invalidResult)
    }

    @Test
    fun `firstNotNull should return first non-null value`() {
        val result = firstNotNull(null, null, "first", "second")
        assertEquals("first", result)
    }

    @Test
    fun `firstNotNull should return null when all values are null`() {
        val result = firstNotNull(null, null, null)
        assertNull(result)
    }

    @Test
    fun `ifNotNullOrElse should execute notNull action when value is not null`() {
        val value: String? = "test"
        var notNullExecuted = false
        var nullExecuted = false

        value.ifNotNullOrElse(
            notNullAction = {
                notNullExecuted = true
                assertEquals("test", it)
            },
            nullAction = {
                nullExecuted = true
            },
        )

        assertTrue(notNullExecuted)
        assertFalse(nullExecuted)
    }

    @Test
    fun `ifNotNullOrElse should execute null action when value is null`() {
        val value: String? = null
        var notNullExecuted = false
        var nullExecuted = false

        value.ifNotNullOrElse(
            notNullAction = {
                notNullExecuted = true
            },
            nullAction = {
                nullExecuted = true
            },
        )

        assertFalse(notNullExecuted)
        assertTrue(nullExecuted)
    }

    // REMOVED: Tests for orElse - Use stdlib's ?: (elvis) operator instead
    // 제거됨: orElse 테스트 - stdlib의 ?: (elvis) 연산자 사용 권장
    
    @Test
    fun `stdlib elvis operator should work as orElse replacement`() {
        val value: String? = "original"
        val result = value ?: "default"
        assertEquals("original", result)
        
        val nullValue: String? = null
        val nullResult = nullValue ?: "default"
        assertEquals("default", nullResult)
    }

    // REMOVED: Tests for takeIfNotNull - Use stdlib's ?.takeIf { ... } instead
    // 제거됨: takeIfNotNull 테스트 - stdlib의 ?.takeIf { ... } 사용 권장
    
    @Test
    fun `stdlib takeIf should work as takeIfNotNull replacement`() {
        val value: String? = "test"
        val result = value?.takeIf { it.startsWith("te") }
        assertEquals("test", result)
        
        val falseResult = value?.takeIf { it.startsWith("ab") }
        assertNull(falseResult)
        
        val nullValue: String? = null
        val nullResult = nullValue?.takeIf { it.startsWith("te") }
        assertNull(nullResult)
    }
}
