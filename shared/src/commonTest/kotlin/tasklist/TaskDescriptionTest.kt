package tasklist

import tasklist.domain.TaskDescription
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

/**
 * Here are some example multiplatform unit tests. They will be compiled for the JVM and to
 * JavaScript. If you opened this file in IntelliJ IDEA: After clicking on the green Play button,
 * you can choose on which platform (JVM or JS) the test should be executed.
 *
 * Note the import statements on top. The @Test-annotation and assertion functions are provided
 * by the Kotlin Multiplatform project, not by some third party library.
 *
 * Unfortunately we can't use Kotlin's support for whitespace characters in method names here (e.g.
 * fun `my test function`() {...}), because they are not supported by JavaScript. Therefor we have
 * to fall back to snake_case_naming (e.g. fun my_test_function() {...}).
 */
class TaskDescriptionTest {

    @Test
    fun a_valid_task_description_has_at_least_4_characters() {
        val description = TaskDescription("test")

        assertEquals("test", description.text)
    }

    @Test
    fun a_blank_description_is_not_allowed() {
        assertFailsWith<Exception> {
            TaskDescription("   ")
        }
    }

    @Test
    fun a_description_with_less_than_4_characters_is_not_allowed() {
        assertFailsWith<Exception> {
            TaskDescription("aaa")
        }
    }

    @Test
    fun a_description_with_more_than_255_characters_is_not_allowed() {
        assertFailsWith<Exception> {
            TaskDescription("a".repeat(256))
        }
    }

    @Test
    fun a_description_with_line_breaks_is_not_allowed() {
        assertFailsWith<Exception> {
            TaskDescription(
                """
                    This is a description with line-breaks.
                """
            )
        }
    }
}
