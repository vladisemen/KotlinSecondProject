import models.User

class UserDB {
    val users: List<User> = listOf(User(
            1,
            "admin",
            "0000"
    ), User(
            2,
            "user",
            "zzz"
    ))

    /**
     * Есть ли такой логин в БД?
     */
    fun hasLogin(login: String): Boolean {
        return true
    }

    /**
     * найдет и вернет пароль в "БД"
     */
    fun findPasswordByLogin(login: String): String {
        return login
    }
}


