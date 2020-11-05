import models.User

class UserDB {
    private val users: List<User> = listOf(User(
            1,
            "admin",
            "a6de4326f6ec9d8ae3cb7b8e42015027",
            "SALT"
    ), User(
            2,
            "user",
            "fced8b3406938ea846f6eaf0c93dec1a",
            "SALT"
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
    fun findSaltByLogin(login: String): String {
        val user = users.find { it.login == login }
        return user!!.salt!!
    }
}


