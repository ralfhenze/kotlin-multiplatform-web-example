package tasklist

import io.javalin.Javalin
import tasklist.domain.Task
import tasklist.domain.TaskDescription
import tasklist.domain.TaskId
import tasklist.web.TaskSchema
import java.lang.Exception

fun main() {
    var id = 0;
    val tasks = mutableMapOf<TaskId, Task>()

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
        ctx.json(TaskSchema(
            id = task.id.id,
            state = task.state,
            description = task.description.text,
        ))
    }
}
