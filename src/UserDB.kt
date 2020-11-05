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
        return findUserByLogin(login) != null
    }

    /**
     * найдет и вернет пароль в "БД"
     */
    fun findPasswordByLogin(login: String): String {
        return findUserByLogin(login)!!.pass
    }

    /**
     * найдет и вернет сол по логину
     */
    fun findSaltByLogin(login: String): String {
        return findUserByLogin(login)!!.salt!!
    }

    /**
     * Найдет и вернет юзера по логину
     */
    private fun findUserByLogin(login: String): User? {
        return users.find { it.login == login }
    }
}


