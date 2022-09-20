package tasklist

import io.javalin.Javalin
import io.javalin.plugin.openapi.OpenApiOptions
import io.javalin.plugin.openapi.OpenApiPlugin
import io.javalin.plugin.openapi.ui.SwaggerOptions
import io.swagger.v3.oas.models.info.Info
import tasklist.persistence.TaskRepositoryInMemory
import tasklist.web.TaskController
import java.lang.Exception

class TasklistBackendApp {

    companion object {
        const val API_NAME = "Task List Backend API"
        const val SWAGGER_CATEGORY_TASK = "Task"
    }

    private val taskRepository = TaskRepositoryInMemory()

    fun run() {
        val app = Javalin
            .create { config ->
                config.enableCorsForAllOrigins()
                config.enableDevLogging()
                config.registerPlugin(
                    OpenApiPlugin(
                        OpenApiOptions(
                            Info()
                                .version("0.0.0")
                                .title(API_NAME)
                                .description(
                                    "A REST-like JSON-based API to create, retrieve " +
                                        "and delete tasks."
                                )
                        )
                        .path("/api/tasklist-backend-open-api.json")
                        .swagger(
                            SwaggerOptions("/api/swagger-ui")
                                .title(API_NAME)
                        )
                    )
                )
            }
            .start(7070)

        app.exception(Exception::class.java) { ex, ctx ->
            ctx.status(400)
            ctx.result(ex.message ?: "")
        }

        val controller = TaskController(app, taskRepository)
        controller.createTask()
        controller.getAllTasks()
        controller.deleteTask()
    }

    fun reset() {
        taskRepository.clear()
    }
}
