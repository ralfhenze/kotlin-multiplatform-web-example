package tasklist

import io.javalin.Javalin
import io.javalin.plugin.openapi.OpenApiOptions
import io.javalin.plugin.openapi.OpenApiPlugin
import io.javalin.plugin.openapi.dsl.document
import io.javalin.plugin.openapi.dsl.documented
import io.javalin.plugin.openapi.ui.SwaggerOptions
import io.swagger.v3.oas.models.info.Info
import tasklist.domain.Task
import tasklist.domain.TaskDescription
import tasklist.domain.TaskId
import tasklist.schema.TaskSchema
import java.lang.Exception

class TasklistBackendApp {

    private var id = 0;
    private val tasks = mutableMapOf<TaskId, Task>()

    companion object {
        const val API_NAME = "Task List Backend API"
        const val SWAGGER_CATEGORY_TASK = "Task"
    }

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

        registerCreateTaskEndpoint(app)
        registerGetAllTasksEndpoint(app)
        registerDeleteTaskEndpoint(app)
    }

    fun reset() {
        id = 0
        tasks.clear()
    }

    private fun registerCreateTaskEndpoint(app: Javalin) {
        val documentation = document()
            .operation {
                it.summary("Creates a new task")
                it.addTagsItem(SWAGGER_CATEGORY_TASK)
            }
            .body<TaskSchema>()
            .json<TaskSchema>("201")
            .result<String>("400")

        app.post("/api/task", documented(documentation) { ctx ->
            val taskSchema = ctx.bodyAsClass<TaskSchema>()

            id++

            val task = Task(
                id = TaskId(id),
                state = taskSchema.state,
                description = TaskDescription(taskSchema.description),
            )

            tasks[task.id!!] = task

            ctx.status(201)
            ctx.json(taskToSchema(task))
        })
    }

    private fun registerGetAllTasksEndpoint(app: Javalin) {
        val documentation = document()
            .operation {
                it.summary("Returns a list of all tasks")
                it.addTagsItem(SWAGGER_CATEGORY_TASK)
            }
            .jsonArray<TaskSchema>("200")

        app.get("/api/task", documented(documentation) { ctx ->
            ctx.json(tasks.values.map { taskToSchema(it) })
        })
    }

    private fun registerDeleteTaskEndpoint(app: Javalin) {
        val documentation = document()
            .operation {
                it.summary("Deletes an existing task")
                it.addTagsItem(SWAGGER_CATEGORY_TASK)
            }
            .result<String>("200")
            .result<String>("404")
            .result<String>("400")

        app.delete("/api/task/{taskId}", documented(documentation) { ctx ->
            val taskIdString = ctx.pathParam("taskId")
            val taskIdInt = taskIdString.toIntOrNull()

            if (taskIdInt != null) {
                val taskId = TaskId(taskIdInt)
                if (tasks.containsKey(taskId)) {
                    tasks.remove(TaskId(taskIdInt))
                    ctx.status(200)
                } else {
                    ctx.status(404)
                    ctx.result("Task $taskIdString doesn't exist")
                }
            } else {
                ctx.status(400)
                ctx.result("Task ID in path must be an integer, but was \"$taskIdString\"")
            }
        })
    }

    private fun taskToSchema(task: Task): TaskSchema {
        return TaskSchema(
            id = task.id?.id,
            state = task.state,
            description = task.description.text,
        )
    }
}
