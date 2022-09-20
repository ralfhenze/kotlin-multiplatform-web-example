@file:JsExport

package tasklist.web

import tasklist.domain.TaskState
import kotlin.js.JsExport

data class TaskSchema(
    val id: Int?,
    val state: TaskState,
    val description: String,
)
