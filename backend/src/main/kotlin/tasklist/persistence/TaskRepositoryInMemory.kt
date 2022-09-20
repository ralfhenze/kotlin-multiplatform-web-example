package tasklist.persistence

import tasklist.domain.Task
import tasklist.domain.TaskId

class TaskRepositoryInMemory : TaskRepository {

    private var id = 0;
    private val tasks = mutableMapOf<TaskId, Task>()

    override fun createNewTask(task: Task): Task {
        id++

        val taskWithId = task.copy(
            id = TaskId(id),
        )

        tasks[taskWithId.id!!] = taskWithId

        return taskWithId
    }

    override fun getAllTasks(): List<Task> {
        return tasks.values.toList()
    }

    override fun deleteTask(taskId: TaskId): Boolean {
        return if (tasks.containsKey(taskId)) {
            tasks.remove(taskId)
            true
        } else {
            false
        }
    }

    override fun clear() {
        id = 0
        tasks.clear()
    }
}
