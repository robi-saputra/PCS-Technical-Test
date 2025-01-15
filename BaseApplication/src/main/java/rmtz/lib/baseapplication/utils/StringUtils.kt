package rmtz.lib.baseapplication.utils

fun String?.parseNames(): Pair<String, String> {
    return if (this != null) {
        val nameParts = this.split(" ")
        val firstName = nameParts.firstOrNull() ?: ""
        val lastName = nameParts.drop(1).joinToString(" ")
        firstName to lastName
    } else {
        "" to ""
    }
}