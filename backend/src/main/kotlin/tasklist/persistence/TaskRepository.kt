package tasklist.persistence

import tasklist.domain.Task
import tasklist.domain.TaskId

interface TaskRepository {
    fun createNewTask(task: Task): Task
    fun getAllTasks(): List<Task>
    fun deleteTask(taskId: TaskId): Boolean
    fun clear()
}
