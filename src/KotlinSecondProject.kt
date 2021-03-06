import models.Roles
import java.math.BigInteger
import java.security.MessageDigest

fun main(args: Array<String>) {
    if (!checkAmountParams(args)) {
        println("неверное число параметров")
        System.exit(1)
    }
    val arguments = parseValues(args)

    when {
        arguments.isEmpty() -> {
            printHelp()
            System.exit(1)
        }
        arguments.hasHelp() -> {
            printHelp()
            System.exit(1)
        }
        arguments.hasAuthentification() -> {
            val userDB = UserDB()
            var code = authenticate(arguments.login, arguments.pass, userDB) // code 0, 2, 3 или 4
            print("это код с аутентификации - $code")
            if (code !== 0) {
                System.exit(code)
            }
            if (arguments.hasAuthorization()) {
                code = authorization(arguments.role!!, arguments.res!!, arguments.login!!, userDB) // code 0, 6, 5
                print("это код с авторизации - $code")
                System.exit(code)
            }

        }
    }
}

/**
 * Вернет код возврата по логину и паролю(0,5 или 6)
 */
fun authorization(roleString: String, res: String, loginUser: String, userDB: UserDB): Int {
    val roleEnum: Roles = roleStringToEnum(roleString) ?: return 5
    return if (userDB.checkResourceAccess(res, roleEnum, loginUser)) {
        0
    } else {
        6
    }
}

/**
 * Вернет роль типа Enum если соотв, иначе вернет null
 */
fun roleStringToEnum(roleString: String): Roles? {
    return when (roleString) {
        "READ" -> {
            Roles.READ
        }
        "WRITE" -> {
            Roles.WRITE
        }
        "EXECUTE" -> {
            Roles.EXECUTE
        }
        else -> {
            null
        }
    }
}

/**
 * проверяет формат логина вернет true если все ок
 */
fun isLoginValid(login: String): Boolean {
    val mathResult = Regex("[^a-zA-Z0-9]").find(login)
    return mathResult != null
}

/**
 * Вернет код возврата по логину и паролю(0,2,3 или 4)
 */
fun authenticate(login: String?, pass: String?, userDB: UserDB): Int {
    return when {
        isLoginValid(login!!) -> {
            2
        }
        !userDB.hasLogin(login) -> {
            3
        }
        !isValidatePassword(pass!!, userDB.findPasswordByLogin(login), userDB.findSaltByLogin(login)) -> {
            4
        }
        else -> {
            0
        }
    }
}

fun isValidatePassword(passArg: String, passDB: String, salt: String): Boolean {
    return getPassHashAndSolt(passArg, salt) == passDB
}

/**
 * Вернет хэшированный пароль (с добавлением соли)
 */
fun getPassHashAndSolt(pass: String, salt: String): String {
    return getHash(getHash(pass) + salt)
}

/**
 * Вернет хэш строки (MD5)
 */
fun getHash(password: String): String {
    val md = MessageDigest.getInstance("MD5")
    return BigInteger(1, md.digest(password.toByteArray())).toString(16).padStart(32, '0')
}

fun checkAmountParams(args: Array<String>): Boolean {
    return (args.isEmpty() || args.size == 1 || args.size == 4 || args.size == 8 || args.size == 14)
}

fun parseValues(args: Array<String>): Arguments {
    return when (args.size) {
        4 -> {
            Arguments(false, args[1], args[3], null, null, null, null, null)
        }
        8 -> {
            Arguments(false, args[1], args[3], args[5], args[7], null, null, null)
        }
        14 -> {
            Arguments(false, args[1], args[3], args[5], args[7], args[9], args[11], args[13])
        }
        else -> {
            Arguments(true, null, null, null, null, null, null, null)
        }
    }
}

fun printHelp() {
    println("Это справка")
}


