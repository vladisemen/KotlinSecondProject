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
            val code = authenticate(arguments.login, arguments.pass) // code 0, 2, 3 или 4
            System.exit(code) // Когда начнем переписывать нам придется переделать этот момент
        }
    }
}

/**
 * проверяет формат логина
 */
fun isLoginValid(login: String?): Boolean {
    return true
}

/**
 * Вернет код возврата по логину и паролю(0,2,3 или 4)
 */
fun authenticate(login: String?, pass: String?): Int {
    return 0
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


