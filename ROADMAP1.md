# План проект<br>

1. Создание консольного приложения на языке kotlin
	
	1.1 Создание общего репозитория 

2. Создать классы: пользователя, ресурса и Function 

	2.1. Класс пользователя содержит поля: логин, пароль и права

		2.1.1 Пароль хранится в хэш и соль виде
		
		2.1.2 Возможности пользователя представлены в виде констант: READ, WRITE, EXECUTE (константы? Enum прав?)

	2.2. Класс пользователя содержит поле расположения узла

		2.2.1. Ресурса представлено в формате "узел"."узел"
	
   		2.2.2. Метод, кт получает расположение узла в формате А.B.С, а возвращает массив узлов, к кт есть доступ

	2.3. В классе Function будут храниться функции: аутентификации, авторизации, аккаунтинг, вывод справки, информцию о ресурсе   
	
	2.4 Наример
		
		2.4.1  пользователь "admin1"  ресурс "Ресурс 1" роль "READ"
		2.4.2  пользователь "admin1"  ресурс "Ресурс 2" роль "EXECUTE"
		2.4.3  пользователь "admin2"  ресурс "Ресурс 3" роль "EXECUTE"
		2.4.4  пользователь "admin2"  ресурс "Ресурс 4" роль "EXECUTE"
			
3. Добавить функцию проверки 

	3.1. Проверяет формат логина 
 
	3.2. Проверяет формат пароля 
 
 	3.3. Проверяет формат ресурса 
     
 	3.4. Проверяет существование роли 
 
 	3.5. Проверяет формат дат 
 	
 	3.6. Проверяет формат значения объема 
 
	3.7. Проверяет необходимость вывода справки.
     
4. Добавить функцию аутентификации

	4.1. Будет проверять связку логин+пароль на соответствие с данными в файле

5. Добавить функцию авторизации

	5.1. Будет проверять возможности пользователя к определенному ресурсу

6. Добавить функцию аккаунтинга

	6.1 Будет сохранять информацию о пользователе
	
7. Добавить функцию "Вывод справки'

	7.1. Выводит информацию о командах

8. Добавить функцию "Информацию о ресурсе"
	
	8.1 Учитывает время доступа к заданному ресурсу и потребленный объем, и выводит его на экран 

9. Добавить функции с exit кодами

	9.1. Вариации exit кодов:
	  - аутентификации принимает логин, пароль и проверяет их подлинность. Возвращает exit коды (0, 1, 2, 3, 4).
	  - авторизации принимает роль и проверяет доступ. Возвращает exit коды (0, 5, 6)
	  - аккаунтинга принимает начальную и конечную дату. Возвращает exit коды (0, 7)
	  - функция справки, если функция аутентификации вернет код 1
 
10. Создать файл .bat с тестовыми сценарями (сценарии актуальны для пункта 2.4)

	10.1 Проверка на аутентификации

		10.1.1 Ввод данных:-login "admin" -password "admin". Результат: Успешная аутентификация (exit код 0)

		10.1.2 Ввод данных:-login "ad min" -password "admin". Результат: Неверный логин (exit код 2)

		10.1.3 Ввод данных:-login "admin" -password "ad min". Результат: Неверный пароль (exit код 4)
	
	10.2 Проверка на авторизацию

		10.2.1 Ввод данных: -login "admin" -password "admin1" -res "A.AB.ABC" -role "READ". Результат: Успешная авторизация (exit код 0)

		10.2.2 Ввод данных: -login "admin" -password "admin1" -res "A.AB.ABC" -role "RE". Результат: Роль не найдена или не существует (exit код 5)

		10.2.3 Ввод данных: -login "admin" -password "admin1" -res "A.AB.ABC" -role "". Результат: Передача пустого аргумента в -role (exitCode 5)

		10.2.4 Ввод данных: -login "admin" -password "admin1" -res "A.AB.ABC" -role "WRITE". Результат: Работа с ресурсом не доступна для этого пользователя (exitCode 6)
		
		10.2.5 Ввод данных: -login "admin" -password "admin1" -res "A.AB.ABD" -role "WRITE". Результат: Работа с ресурсом не доступна для этого пользователя (exitCode 6)

	10.3 Проверка на Аккаунтинг

		10.3.1 Ввод данных: -login "admin2" -password "admin2" -res "A.AB.ABC" -role "READ" -ds "01-01-2020" -de "01-06-2020" -vol "10,1". Результат: Ошибка. В объеме есть дробное число или текст (exitCode 7)

		10.3.2 Ввод данных: -login "admin2" -password "admin2" -res "A.AB.ABC" -role "READ" -ds "01.01.2020" -de "01.06.2020" -vol "10". Результат: Несоблюдением формата даты (exitCode 7)

		1..3.2 Ввод данных: -login "admin2" -password "admin2" -res "A.AB.ABC" -role "READ" -ds "01-01-2020" -de "01-06-2020" -vol "10". Результат: Успешная авторизация (exit код 0)

	10.4 Вывод справки (exitCode 1)

