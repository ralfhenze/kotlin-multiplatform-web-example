package tasklist.schema

import tasklist.domain.TaskState
import tasklist.web.TaskSchema

data class TaskSchema(
    override val id: Int?,
    override val state: TaskState,
    override val description: String,
) : TaskSchema()
