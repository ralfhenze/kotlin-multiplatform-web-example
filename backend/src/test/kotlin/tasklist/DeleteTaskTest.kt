package tasklist

import kong.unirest.HeaderNames
import kong.unirest.Unirest
import net.javacrumbs.jsonunit.assertj.assertThatJson
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import tasklist.util.BackendApiEndpointTest

@DisplayName("DELETE /api/task/{taskId}")
class DeleteTaskTest : BackendApiEndpointTest() {

    @Test
    fun `deletes the task of given ID`() {

        // Given task #1 exists
        Unirest
            .post("$BACKEND_URL/api/task")
            .body("""{"state": "TODO", "description": "My first task"}""")
            .asEmpty()

        // And task #2 exists
        Unirest
            .post("$BACKEND_URL/api/task")
            .body("""{"state": "IN_PROGRESS", "description": "My second task"}""")
            .asEmpty()

        // When we attempt to delete task #1
        val response = Unirest
            .delete("$BACKEND_URL/api/task/1")
            .asString()

        // Then it gets deleted
        assertThat(response.status).isEqualTo(200)
        assertThat(response.body).isEmpty()

        // And task #2 is left over
        assertThatJson(Unirest.get("$BACKEND_URL/api/task").asString().body).isEqualTo("""
            [
                {
                    "id": 2,
                    "state": "IN_PROGRESS",
                    "description": "My second task"
                }
            ]
        """)
    }

    @Test
    fun `returns 404 - Not Found when task of given ID doesn't exist`() {

        // Given no tasks exist

        // When we attempt to delete task #1
        val response = Unirest
            .delete("$BACKEND_URL/api/task/1")
            .asString()

        // Then we get an error
        assertThat(response.status).isEqualTo(404)
        assertThat(response.headers.getFirst(HeaderNames.CONTENT_TYPE)).isEqualTo("text/plain")
        assertThat(response.body).isEqualTo("Task 1 doesn't exist")
    }

    @Test
    fun `returns 400 - Bad Request when task ID is no integer`() {

        // When we attempt to delete a task by providing a non-numeric ID
        val response = Unirest
            .delete("$BACKEND_URL/api/task/some-string")
            .asString()

        // Then we get an error
        assertThat(response.status).isEqualTo(400)
        assertThat(response.headers.getFirst(HeaderNames.CONTENT_TYPE)).isEqualTo("text/plain")
        assertThat(response.body).isEqualTo("Task ID in path must be an integer, but was \"some-string\"")
    }

    @Test
    fun `returns 400 - Bad Request when task ID is smaller than 1`() {

        // When we attempt to delete a task by providing an invalid numeric ID
        val response = Unirest
            .delete("$BACKEND_URL/api/task/0")
            .asString()

        // Then we get an error
        assertThat(response.status).isEqualTo(400)
        assertThat(response.headers.getFirst(HeaderNames.CONTENT_TYPE)).isEqualTo("text/plain")
        assertThat(response.body).isEqualTo("The task ID must be >= 1, but was 0")
    }
}
