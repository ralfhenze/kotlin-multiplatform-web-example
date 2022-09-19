package tasklist

import kong.unirest.HeaderNames.CONTENT_TYPE
import kong.unirest.Unirest
import net.javacrumbs.jsonunit.assertj.assertThatJson
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("POST /api/task")
class CreateTaskTest {

    companion object {
        @BeforeAll
        @JvmStatic
        fun startBackend() {
            main()
        }
    }

    @Test
    fun `returns 201 - Created and the created task with an ID`() {
        val response = Unirest
            .post("http://localhost:7070/api/task")
            .body("""
                {
                    "state": "TODO",
                    "description": "Some nice task!"
                }
            """)
            .asString()

        assertThat(response.status).isEqualTo(201)
        assertThat(response.headers.getFirst(CONTENT_TYPE)).isEqualTo("application/json")
        assertThatJson(response.body).isEqualTo("""
            {
                "id": 1,
                "state": "TODO",
                "description": "Some nice task!"
            }
        """)
    }

    @Test
    fun `returns 400 - Bad Request when received no JSON-object of TaskSchema`() {
        val response = Unirest
            .post("http://localhost:7070/api/task")
            .body("""
                {
                    "someUnexpectedProperty": 1
                }
            """)
            .asString()

        assertThat(response.status).isEqualTo(400)
        assertThat(response.body).isEmpty()
    }
}
