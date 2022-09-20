@file:JsExport

package tasklist.web

import tasklist.domain.TaskState
import kotlin.js.JsExport

abstract class TaskSchema {
    abstract val id: Int?
    abstract val state: TaskState
    abstract val description: String
}

/**
 * Defining an interface instead of an abstract class doesn't work currently, due to
 * https://youtrack.jetbrains.com/issue/KT-51974/KJS-IR-all-APIs-infected-with-readonly-doNotUseIt-doNotImplementIt
 *
 * The IR compiler generates an additional "__doNotUseIt" property in the TypeScript interface,
 * which all implementations of that interface would also need to implement. It's probably put
 * there, to prevent developers from using interfaces. Maybe due to an unfinished implementation.
 */
//interface TaskSchema {
//    val id: Int?
//    val state: TaskState
//    val description: String
//}
