@file:JsExport

package tasklist.domain

import kotlin.js.JsExport

class TaskValidator {

    fun getDescriptionErrorMessage(description: String): String? {
        return try {
            TaskDescription(description)
            null
        } catch (ex: Exception) {
            ex.message
        }
    }
}
