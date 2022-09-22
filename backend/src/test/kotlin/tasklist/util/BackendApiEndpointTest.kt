package tasklist.util

import org.junit.jupiter.api.BeforeAll
import tasklist.TasklistBackendApp

/**
 * Baseclass for backend integration tests
 *
 * It spins up the whole backend so that tests can make real HTTP requests against it. Between each
 * test, the database will be wiped, so that no test can depend on each other.
 */
open class BackendApiEndpointTest {

    companion object {
        const val BACKEND_URL = "http://localhost:7070"

        var backend: TasklistBackendApp? = null

        @BeforeAll
        @JvmStatic
        fun startBackendIfNotRunningYet() {
            if (backend == null) {
                backend = TasklistBackendApp()
                backend?.run()
            } else {
                backend?.reset()
            }
        }
    }
}
