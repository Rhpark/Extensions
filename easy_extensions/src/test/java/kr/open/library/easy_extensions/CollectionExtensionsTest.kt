package kr.open.library.easy_extensions

import kr.open.library.easy_extensions.collection.*
import org.junit.Assert.*
import org.junit.Test

/**
 * Unit tests for CollectionExtensions
 * Tests only cover functions that are NOT available in Kotlin stdlib
 */
class CollectionExtensionsTest {
    @Test
    fun `filterIf should filter when condition is true`() {
        val numbers = listOf(1, 2, 3, 4, 5, 6)
        val evenNumbers = numbers.filterIf(true) { it % 2 == 0 }
        assertEquals(listOf(2, 4, 6), evenNumbers)
    }

    @Test
    fun `filterIf should return original list when condition is false`() {
        val numbers = listOf(1, 2, 3, 4, 5, 6)
        val result = numbers.filterIf(false) { it % 2 == 0 }
        assertEquals(numbers, result)
    }

    @Test
    fun `ifNotEmpty should execute action for non-empty list`() {
        val numbers = listOf(1, 2, 3)
        var actionExecuted = false

        numbers.ifNotEmpty {
            actionExecuted = true
            assertEquals(3, it.size)
        }

        assertTrue(actionExecuted)
    }

    @Test
    fun `ifNotEmpty should not execute action for empty list`() {
        val numbers = emptyList<Int>()
        var actionExecuted = false

        numbers.ifNotEmpty {
            actionExecuted = true
        }

        assertFalse(actionExecuted)
    }

    @Test
    fun `ifEmpty should execute action for empty list`() {
        val numbers = emptyList<Int>()
        var actionExecuted = false

        numbers.ifEmpty {
            actionExecuted = true
        }

        assertTrue(actionExecuted)
    }

    @Test
    fun `ifEmpty should not execute action for non-empty list`() {
        val numbers = listOf(1, 2, 3)
        var actionExecuted = false

        numbers.ifEmpty {
            actionExecuted = true
        }

        assertFalse(actionExecuted)
    }

    // Note: Removed tests for functions that duplicate Kotlin stdlib functionality

    @Test
    fun `map ifNotEmpty should execute action for non-empty map`() {
        val map = mapOf("a" to 1, "b" to 2)
        var actionExecuted = false

        map.ifNotEmpty {
            actionExecuted = true
            assertEquals(2, it.size)
        }

        assertTrue(actionExecuted)
    }

    @Test
    fun `map ifNotEmpty should not execute action for empty map`() {
        val map = emptyMap<String, Int>()
        var actionExecuted = false

        map.ifNotEmpty {
            actionExecuted = true
        }

        assertFalse(actionExecuted)
    }
}
