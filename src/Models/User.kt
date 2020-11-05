package models

data class User(
        val id: Int,
        val login: String,
        val pass: String,
        val salt: String?
) {
}