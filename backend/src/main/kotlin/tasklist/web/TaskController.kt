package tasklist.web

import io.javalin.Javalin
import io.javalin.plugin.openapi.dsl.document
import io.javalin.plugin.openapi.dsl.documented
import tasklist.TasklistBackendApp
import tasklist.domain.Task
import tasklist.domain.TaskDescription
import tasklist.domain.TaskId
import tasklist.persistence.TaskRepository
import tasklist.schema.TaskSchema

class TaskController(
    private val app: Javalin,
    private val taskRepository: TaskRepository,
) {

    fun createTask() {
        val documentation = document()
            .operation {
                it.summary("Creates a new task")
                it.addTagsItem(TasklistBackendApp.SWAGGER_CATEGORY_TASK)
            }
            .body<TaskSchema>()
            .json<TaskSchema>("201")
            .result<String>("400")

        app.post("/api/task", documented(documentation) { ctx ->
            val taskSchema = ctx.bodyAsClass<TaskSchema>()

            val task = taskRepository.createNewTask(
                Task(
                    state = taskSchema.state,
                    description = TaskDescription(taskSchema.description),
                )
            )

            ctx.status(201)
            ctx.json(taskToSchema(task))
        })
    }

    fun getAllTasks() {
        val documentation = document()
            .operation {
                it.summary("Returns a list of all tasks")
                it.addTagsItem(TasklistBackendApp.SWAGGER_CATEGORY_TASK)
            }
            .jsonArray<TaskSchema>("200")

        app.get("/api/task", documented(documentation) { ctx ->
            val tasks = taskRepository
                .getAllTasks()
                .map { taskToSchema(it) }

            ctx.json(tasks)
        })
    }

    fun deleteTask() {
        val documentation = document()
            .operation {
                it.summary("Deletes an existing task")
                it.addTagsItem(TasklistBackendApp.SWAGGER_CATEGORY_TASK)
            }
            .result<String>("200")
            .result<String>("404")
            .result<String>("400")

        app.delete("/api/task/{taskId}", documented(documentation) { ctx ->
            val taskIdString = ctx.pathParam("taskId")
            val taskIdInt = taskIdString.toIntOrNull()

            if (taskIdInt != null) {
                val taskId = TaskId(taskIdInt)
                val taskDeleted = taskRepository.deleteTask(taskId)
                if (taskDeleted) {
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
