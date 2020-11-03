import models.User

class UserDB {
    private val users: List<User> = listOf(User(
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
        val user = users.find { it.login == login }
        return user != null
    }

    /**
     * найдет и вернет пароль в "БД"
     */
    fun findPasswordByLogin(login: String): String {
        val user = users.find { it.login == login }
        return user!!.pass
    }
}


