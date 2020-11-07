1. Создаем консольный проект HelloWorld с файлом Main.kt и функцией main
и после запуска видим строку в терминале
2. Тестовые сценарии
2.1. Тестовые данные
Аккаунты:
admin - 0000
user - zzz

Авторизации:
admin READ A
admin READ B
admin READ C
admin WRITE A
admin WRITE B
admin WRITE C
admin EXECUTE A
admin EXECUTE B
admin EXECUTE C

user READ A
user EXECUTE A.B
user WRITE XY.UV.ABCDEFGHIJ

2.2. Тестовые сценарии
2.2.0. Создаём скритпы
build.sh для сборки jar файла
test.sh для запуска тестов

2.2.1. Вывод справки:
T1.1 run.sh (0 - выводится справка)
T1.2 run.sh -h (1 - выводится справка)

2.2.2. Аутентификация
T2.1 run.sh -login admin -pass 0000 (0 - успешный логин)
T2.2 run.sh -pass 0000 -login admin (0 - успешно)
T2.3 run.sh -login ??? -pass 123 (2 - неверный формат)
T2.4 run.sh -login Admin -pass 0000 (3 - неизвестный логин)
T2.5 run.sh -login admin -pass 1111 (4 - неверный пароль)

2.2.3. Авторизация
T3.1 run.sh -login admin -pass 0000 -role READ -res A (0 - успешная авторизация)
T3.2 run.sh -login admin -pass 0000 -role EXECUTE -res B (0 - успешная авторизация)
T3.3 run.sh -login admin -pass 0000 -role WRITE -res C (0 - успешная авторизация)

T3.4 run.sh -login user -pass zzz -role READ -res A (0 - успешная авторизация)
T3.5 run.sh -login user -pass zzz -role READ -res A.B (0 - успешная авторизация)
T3.6 run.sh -login user -pass zzz -role EXECUTE -res A.B.C (0 - успешная авторизация)
T3.7 run.sh -login user -pass zzz -role WRITE -res XY.UV.ABCDEFGHIJ (0 - успешная авторизация)
T3.8 run.sh -login admin -pass 0000 -role WRITE -res A.B.C.D (0 - успешная авторизация)

T3.9 run.sh -login admin -pass 0000 -role DELETE -res A (5 - неизвестная роль)
T3.10 run.sh -login user -pass zzz -role WRITE -res XY (6 - нет доступа)
T3.10 run.sh -login user -pass zzz -role WRITE -res D (6 - нет доступа)

T3.11 run.sh -login user -pass zzz -role EXECUTE -res A.BC (6 - нет доступа)

2.2.4. Аккаунтинг
...

2.3. Написать скрипт для запуска тестов
и сравнения результатов
2.3.1 Подсчитать количество прошедших и упавших тестов
2.3.2 Для интеграции с тревисом
Если количество упавших тестов > 0 то возвращаем 1
Если количество упавших тестов = 0 то возвращаем 0

2.4 Интегрировать проект на гитхабе с travis-ci

3. Упрощенный разбор командной строки

Допущение, на этом моменте программа
проверяет только количество параметров
параметры переданы правильные
не проверяет порядок этих параметров
считывает значения параметров из фиксированных позиций 0, 1, 3, 5, 7, 9, 11, 13 (напр. логин всегда в первой ячейке)
0
1 (-h)
4 (-login xxx -pass yyy)
8 (-login xxx -pass yyy -role aaa -res bbb)
14 (-login xxx -pass yyy -role aaa -res bbb -ds fff -de ggg -vol hhh)

3.1 создать метод проверки количества параметров checkAmountParams(args: Array<String>): Bool
проверим что количество аргументов 0,1,4,8 или 14
(Методы должны быть по-возможности без
side-effects - не должен менять состояние окружени,
stateless - запуск метода не должен зависеть от внешнего состояния,
idempotent - можно метод запускать несколько раз и на одни и те же входящие данные мы получаем тот же результат
)
3.2 создать data class Arguments(
    val h: Boolean,
    val login: String?,
    val pass: String?,
    val role: String?,
    val res: String?,
    val ds: String?,
    val de: String?,
    val vol: String?
)
3.3 создать метод в файле Main.kt который будет доставать значения из аргументов и раскладывать по полям parseValues(args: Array<String>): Arguments
3.4 вызвать parseValues в методе main и записать его в переменную val arguments

4. Вывод справки
4.1 в классе Main.kt создаём метод printHelp() который выведет справку
4.2 в классе Arguments создаём метод который проверяет что ни одного параметра нет isEmpty():Boolean
4.3 в классе Arguments создаём метод который проверяет есть параметр -h hasHelp():Boolean
4.4 в метод main дописываем оператор when который будет реагировать на количество входных параметров
4.4.1 добавляем проверки на справку
when {
    arguments.isEmpty() -> {
        printHelp()
        System.exit(1)
    }
    arguments.hasHelp() -> {
        printHelp()
        System.exit(1) // В kotlinx.cli вы не сможете повлиять на код возврата который будет всегда 0
    }
}

? Метод с регулярками

5. Написать код для аутентификации
5.1 В Arguments добавляем метода hasAuthentification(): Boolean
5.2 Добавляем проверку в when
arguments.hasAuthentification() -> {
    // TODO
}
5.3 создаем метод внутри main authenticate(login: String, pass: String): Long который будет проводить аутентификацию 
и возвращать код возврата
5.4 внутрь hasAuthentification добавить вызов authenticate
arguments.hasAuthentification() -> {
    val code = authenticate(arguments.login, arguments.pass) // code 0, 2, 3 или 4
    System.exit(code) // Когда начнем переписывать нам придется переделать этот момент
}
5.5 создать метод внутри main isLoginValid(): Boolean который будет проверять на формат логина
5.6 внутри authenticate вызываем isLoginValid и если результат false то возвращаем 2 иначе проверяем дальше

5.7 Создаём "базу данных" пользователей

5.7.1 Создаём класс UserDB (потом мы его переименуем в UserService)
5.7.2 В классе UserDB создаём метод hasLogin(login: String): Boolean который проверяет наличие логина // Метод пустой на текущий момент
5.7.3 В методе main создать экземпляр класса UserDB и передать его внутрь метода authenticate
5.7.4 внутри authenticate вызываем userDB.hasLogin если вернул false возвращаем 3 иначе обрабатываем дальше
5.7.5 В классе UserDB создаём метод findPasswordByLogin(login: String): String // Метод пустой на текущий момент
5.7.6 внутри authenticate проверяем совпадает ли пароль с найденным в базе validatePassword(passArg: String, passDB: String): Boolean
если false то вернём 4 иначе вернём 0

// Абстракция и Инкапсуляция позволяет мысленно сосредотачиваться на чем-то одном
5.8 Методы внутри базы данных
5.8.1 Мы должны создать класс User
data class User(
    val id: Long,
    val login: String,
    val pass: String
)
5.8.2 Создаем коллекцию users внутри класса UserDB (иммутабельный список) юзеров и заполняем его двумя тестовыми записями
5.8.3 Заменяем заглушку внутри метода hasLogin на реальный код:
перебирам элементы users и сравниваем логин (ФВП any)
5.8.4 Заменяем заглушку внутри метода findPasswordByLogin на реальный код:
перебираем элементы users и ищем пользователя по логину и возвращаем пароль из найденного пользователя

----------------------------------------------------------------------------------------------------------

5.9 Тестирование аутентификации на налачие ошибок и исправление их в случае обнаружения

6 Хранение пароля в безопасном виде(MD-5)

    6.1 Добавить юзеру поле salt: String?
    6.2 В БД заполнить значение поля salt, например одинаковым значением
    6.3 Используя сревис (напр. https://decodeit.ru/md5/) захешировать существующие пароли (hash(hash(pass)+salt)) и изменить данные в "БД"
    6.4 Создать метод хэширования пароля в main.kt getHash(password: String): String
    6.5 Переписать validatePassword(passArg: String, passDB: String, salt: String): Boolean  
    6.6 Добавить метод getPassHashAndSolt (pass: String, salt: String) : String который будет возвращать пароль по соли и хэш
7 Авторизация

    7.1 Создать enum class Roles() {
                                        READ,
                                        WRITE,
                                        EXECUTE
                                    }
                                    
    7.2 Создать дата класс data class RoleResource(
                                val role: Roles,
                                val resource: String,
                                val idUser: Long)
                                
    7.3  Создаем коллекцию rolesResources внутри класса UserDB (иммутабельный список) и заполняем его тестовыми записями (п 2.2.3)
    
    7.4  Создаем метод внутри main authorization(roleString: String, res: String, idUser: Long): Long который будет проводить авторизацию и возвращать код возврата
    
    7.5 Создаем метода в классе RoleResource который проверяет имеет ли пользователь доступ checkResourceAccess(resource: String, role: Roles): Boolean (здесь ФВП)
    
8 Аккаунтинг

