package tasklist.domain

data class TaskDescription(
    val text: String,
) {
    companion object {
        const val MIN_LENGTH = 4
        const val MAX_LENGTH = 255
    }

    init {
        if (text.isBlank()) {
            throw Exception("Please provide a description.")
        }
        if (text.trim().length < MIN_LENGTH) {
            throw Exception("The description must have at least $MIN_LENGTH characters.")
        }
        if (text.trim().length > MAX_LENGTH) {
            throw Exception("The description must not have more than $MAX_LENGTH characters.")
        }
        if (text.contains("\n") || text.contains("\r")) {
            throw Exception("The description must not contain line-breaks.")
        }
    }
}
