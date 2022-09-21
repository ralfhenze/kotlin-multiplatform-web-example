@file:JsExport

package tasklist.i18n

import tasklist.domain.TaskState
import kotlin.js.JsExport

class EnUsLocalization : Localization {

    override fun getTaskStateLabel(taskState: TaskState): String {
        return when (taskState) {
            TaskState.TODO -> "TODO"
            TaskState.IN_PROGRESS -> "IN PROGRESS"
            TaskState.DONE -> "DONE"
        }
    }
}
