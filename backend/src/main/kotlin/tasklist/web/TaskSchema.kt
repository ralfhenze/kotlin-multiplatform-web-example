package tasklist.web

import tasklist.domain.TaskState

data class TaskSchema(
    val id: Int?,
    val state: TaskState,
    val description: String,
)
