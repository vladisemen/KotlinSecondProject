import models.Roles
import models.User

class UserDB {
    private val rolesResources: List<RoleResource> = listOf(
            //region admin
            RoleResource(
                    Roles.READ,
                    "A",
                    1
            ),
            RoleResource(
                    Roles.READ,
                    "B",
                    1
            ),
            RoleResource(
                    Roles.READ,
                    "C",
                    1
            ),
            RoleResource(
                    Roles.WRITE,
                    "A",
                    1
            ),
            RoleResource(
                    Roles.WRITE,
                    "B",
                    1
            ),
            RoleResource(
                    Roles.WRITE,
                    "C",
                    1
            ),
            RoleResource(
                    Roles.EXECUTE,
                    "A",
                    1
            ),
            RoleResource(
                    Roles.EXECUTE,
                    "B",
                    1
            ),
            RoleResource(
                    Roles.EXECUTE,
                    "C",
                    1
            ),
            //endregion
            //region user
            RoleResource(
                    Roles.READ,
                    "A",
                    2
            ),
            RoleResource(
                    Roles.EXECUTE,
                    "A.B",
                    2
            ),
            RoleResource(
                    Roles.WRITE,
                    "XY.UV.ABCDEFGHIJ",
                    2
            ),
            //endregion
    )
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
     * Имеет ли доступ к ресурсу
     */
    fun checkResourceAccess(resource: String, role: Roles, idUser: Int): Boolean{
        return true
    }

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


