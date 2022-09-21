@file:JsExport

package tasklist.i18n

import tasklist.domain.TaskState
import kotlin.js.JsExport

interface Localization {
    fun getTaskStateLabel(taskState: TaskState): String
}
