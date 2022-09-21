package tasklist.domain

data class TaskDescription(
    val text: String,
) {
    init {
        if (text.isBlank()) {
            throw Exception("Please provide a description")
        }
        if (text.trim().length <= 3) {
            throw Exception("The description must be longer than 3 characters")
        }
    }
}
