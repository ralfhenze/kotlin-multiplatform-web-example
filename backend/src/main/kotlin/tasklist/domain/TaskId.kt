package tasklist.domain

data class TaskId(
    val id: Int,
) {
    init {
        if (id <= 0) {
            throw Exception("The task ID must be >= 1, but was $id")
        }
    }
}
