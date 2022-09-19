package tasklist

import kong.unirest.HeaderNames.CONTENT_TYPE
import kong.unirest.Unirest
import net.javacrumbs.jsonunit.assertj.assertThatJson
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import tasklist.util.BackendApiTest

@DisplayName("GET /api/task")
class GetAllTasksTest : BackendApiTest() {

    @Test
    fun `returns a list of all tasks`() {
        Unirest
            .post("$BACKEND_URL/api/task")
            .body("""{"state": "TODO", "description": "My first task"}""")
            .asEmpty()

        Unirest
            .post("$BACKEND_URL/api/task")
            .body("""{"state": "IN_PROGRESS", "description": "My second task"}""")
            .asEmpty()

        val response = Unirest
            .get("$BACKEND_URL/api/task")
            .asString()

        assertThat(response.status).isEqualTo(200)
        assertThat(response.headers.getFirst(CONTENT_TYPE)).isEqualTo("application/json")
        assertThatJson(response.body).isEqualTo("""
            [
                {
                    "id": 1,
                    "state": "TODO",
                    "description": "My first task"
                },
                {
                    "id": 2,
                    "state": "IN_PROGRESS",
                    "description": "My second task"
                }
            ]
        """)
    }
}
