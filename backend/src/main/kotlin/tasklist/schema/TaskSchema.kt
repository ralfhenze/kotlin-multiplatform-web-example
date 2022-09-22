package tasklist.schema

import io.swagger.v3.oas.annotations.media.Schema
import tasklist.domain.TaskDescription
import tasklist.domain.TaskState
import tasklist.web.TaskSchema

data class TaskSchema(

    @Schema(
        description = "Numeric ID of the task, must be >= 1",
        example = "1",
        nullable = true,
    )
    override val id: Int?,

    @Schema(
        description = "Current state of the task",
        example = "TODO",
        nullable = false,
    )
    override val state: TaskState,

    @Schema(
        description =
            "Free-text description of the task, must not be blank and not contain line-breaks",
        example = "Buy some food",
        minLength = TaskDescription.MIN_LENGTH,
        maxLength = TaskDescription.MAX_LENGTH,
        nullable = false,
    )
    override val description: String,

) : TaskSchema()
