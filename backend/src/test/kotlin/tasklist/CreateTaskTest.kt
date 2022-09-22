package tasklist

import kong.unirest.HeaderNames.CONTENT_TYPE
import kong.unirest.Unirest
import net.javacrumbs.jsonunit.assertj.assertThatJson
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import tasklist.util.BackendApiEndpointTest

@DisplayName("POST /api/task")
class CreateTaskTest : BackendApiEndpointTest() {

    @Test
    fun `creates a new task and assigns an ID to it`() {

        // When we attempt to create a new task with a valid description
        val response = Unirest
            .post("$BACKEND_URL/api/task")
            .body("""
                {
                    "state": "TODO",
                    "description": "Some nice task!"
                }
            """)
            .asString()

        // Then it gets created
        assertThat(response.status).isEqualTo(201)
        assertThat(response.headers.getFirst(CONTENT_TYPE)).isEqualTo("application/json")

        // And an ID was assigned to it
        assertThatJson(response.body).isEqualTo("""
            {
                "id": 1,
                "state": "TODO",
                "description": "Some nice task!"
            }
        """)
    }

    @Test
    fun `returns 400 - Bad Request when description contains only whitespace characters`() {

        // When we attempt to create a new task with a blank description
        val response = Unirest
            .post("$BACKEND_URL/api/task")
            .body("""
                {
                    "state": "TODO",
                    "description": "   "
                }
            """)
            .asString()

        // Then we get an error
        assertThat(response.status).isEqualTo(400)
        assertThat(response.body).isEqualTo("Please provide a description.")
    }

    @Test
    fun `returns 400 - Bad Request when received no JSON-object of TaskSchema`() {

        // When we attempt to create a new task, but don't provide the required data
        val response = Unirest
            .post("$BACKEND_URL/api/task")
            .body("""
                {
                    "someUnexpectedProperty": 1
                }
            """)
            .asString()

        // Then we get an error
        assertThat(response.status).isEqualTo(400)
    }
}
