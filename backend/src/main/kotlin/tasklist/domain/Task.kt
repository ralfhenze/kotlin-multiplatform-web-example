package tasklist.domain

data class Task(
    val id: TaskId?,
    val state: TaskState,
    val description: TaskDescription,
)
