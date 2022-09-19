package tasklist

import io.javalin.Javalin
import tasklist.domain.Task
import tasklist.domain.TaskDescription
import tasklist.domain.TaskId
import tasklist.web.TaskSchema
import java.lang.Exception

class TasklistBackendApp {

    private var id = 0;
    private val tasks = mutableMapOf<TaskId, Task>()

    fun run() {
        val app = Javalin.create().start(7070)

        app.exception(Exception::class.java) { _, ctx ->
            ctx.status(400)
        }

        app.post("/api/task") { ctx ->
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
        }

        app.get("/api/task") { ctx ->
            ctx.json(tasks.values.map { taskToSchema(it) })
        }
    }

    fun reset() {
        id = 0
        tasks.clear()
    }

    private fun taskToSchema(task: Task): TaskSchema {
        return TaskSchema(
            id = task.id?.id,
            state = task.state,
            description = task.description.text,
        )
    }
}
