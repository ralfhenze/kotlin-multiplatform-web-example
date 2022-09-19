package tasklist.util

import org.junit.jupiter.api.BeforeAll
import tasklist.TasklistBackendApp

open class BackendApiTest {

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
