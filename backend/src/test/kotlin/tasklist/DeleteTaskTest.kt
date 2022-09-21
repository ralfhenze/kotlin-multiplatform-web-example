package tasklist

import kong.unirest.HeaderNames
import kong.unirest.Unirest
import net.javacrumbs.jsonunit.assertj.assertThatJson
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import tasklist.util.BackendApiTest

@DisplayName("DELETE /api/task/{taskId}")
class DeleteTaskTest : BackendApiTest() {

    @Test
    fun `deletes the task of given ID`() {
        Unirest
            .post("$BACKEND_URL/api/task")
            .body("""{"state": "TODO", "description": "My first task"}""")
            .asEmpty()

        Unirest
            .post("$BACKEND_URL/api/task")
            .body("""{"state": "IN_PROGRESS", "description": "My second task"}""")
            .asEmpty()

        val response = Unirest
            .delete("$BACKEND_URL/api/task/1")
            .asString()

        assertThat(response.status).isEqualTo(200)
        assertThat(response.body).isEmpty()

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
        val response = Unirest
            .delete("$BACKEND_URL/api/task/1")
            .asString()

        assertThat(response.status).isEqualTo(404)
        assertThat(response.headers.getFirst(HeaderNames.CONTENT_TYPE)).isEqualTo("text/plain")
        assertThat(response.body).isEqualTo("Task 1 doesn't exist")
    }

    @Test
    fun `returns 400 - Bad Request when task ID is no integer`() {
        val response = Unirest
            .delete("$BACKEND_URL/api/task/some-string")
            .asString()

        assertThat(response.status).isEqualTo(400)
        assertThat(response.headers.getFirst(HeaderNames.CONTENT_TYPE)).isEqualTo("text/plain")
        assertThat(response.body).isEqualTo("Task ID in path must be an integer, but was \"some-string\"")
    }

    @Test
    fun `returns 400 - Bad Request when task ID is smaller than 1`() {
        val response = Unirest
            .delete("$BACKEND_URL/api/task/0")
            .asString()

        assertThat(response.status).isEqualTo(400)
        assertThat(response.headers.getFirst(HeaderNames.CONTENT_TYPE)).isEqualTo("text/plain")
        assertThat(response.body).isEqualTo("The task ID must be >= 1, but was 0")
    }
}
