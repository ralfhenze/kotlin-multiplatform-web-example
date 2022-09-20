@file:JsExport

package tasklist.i18n

import tasklist.domain.TaskState

class EnUsLocale {

    fun getTaskStateLabel(taskState: TaskState): String {
        return when (taskState) {
            TaskState.TODO -> "TODO"
            TaskState.IN_PROGRESS -> "IN PROGRESS"
            TaskState.DONE -> "DONE"
        }
    }
}
