package tasklist.domain

data class Task(
    val id: TaskId? = null,
    val state: TaskState,
    val description: TaskDescription,
)
