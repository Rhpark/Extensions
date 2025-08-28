package kr.open.library.easy_extensions

import kr.open.library.easy_extensions.null_safety.*
import org.junit.Assert.*
import org.junit.Test

/**
 * Unit tests for NullSafetyExtensions
 */
class NullSafetyExtensionsTest {
    @Test
    fun `ifNotNull should execute action when value is not null`() {
        val value = "test"
        var actionExecuted = false

        value.ifNotNull {
            actionExecuted = true
            assertEquals("test", it)
        }

        assertTrue(actionExecuted)
    }

    @Test
    fun `ifNotNull should not execute action when value is null`() {
        val value: String? = null
        var actionExecuted = false

        value.ifNotNull {
            actionExecuted = true
        }

        assertFalse(actionExecuted)
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

    @Test
    fun `safeCast should return correct type when casting is valid`() {
        val value: Any = "test string"
        val result = value.safeCast<String>()
        assertEquals("test string", result)
    }

    @Test
    fun `safeCast should return null when casting is invalid`() {
        val value: Any = 123
        val result = value.safeCast<String>()
        assertNull(result)
    }

    @Test
    fun `safeCast should return null when value is null`() {
        val value: Any? = null
        val result = value.safeCast<String>()
        assertNull(result)
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

    @Test
    fun `orElse should return original value when not null`() {
        val value: String? = "original"
        val result = value.orElse { "default" }
        assertEquals("original", result)
    }

    @Test
    fun `orElse should return default value when null`() {
        val value: String? = null
        val result = value.orElse { "default" }
        assertEquals("default", result)
    }

    @Test
    fun `takeIfNotNull should return value when not null and predicate is true`() {
        val value: String? = "test"
        val result = value.takeIfNotNull { it.startsWith("te") }
        assertEquals("test", result)
    }

    @Test
    fun `takeIfNotNull should return null when not null but predicate is false`() {
        val value: String? = "test"
        val result = value.takeIfNotNull { it.startsWith("ab") }
        assertNull(result)
    }

    @Test
    fun `takeIfNotNull should return null when value is null`() {
        val value: String? = null
        val result = value.takeIfNotNull { it.startsWith("te") }
        assertNull(result)
    }
}
