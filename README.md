# DB_lab2
Лабораторная работа №2: 

1.Придумать свою предметную область и продумать схему БД для неё. 

2.Реализовать реляционную БД для своей предметной области(все таблицы должны быть не менее чем в третьей нормальной форме) 

3.Критерии к БД: 

   3.1.БД должна быть в третьей нормальной форме или выше; 

   3.2.Минимальное количество таблиц – 2; 

   3.3.Все подключения из GUI должны осуществляться выделенным, не root, пользователем; 

   3.4.Должен существовать как минимум один индекс, созданный вами по выбранному текстовому не ключевому полю; 

   3.5.В одной из таблиц должно присутствовать поле, заполняемое/изменяемое только триггером (например, «общая стоимость бронирования» в таблице «бронирования», которое   автоматически высчитывается при добавлении/изменении/удалении билетов, входящих в это бронирование) 

4.Реализовать программу GUI со следующим функционалом: 

   4.1.Создание базы данных (важно(!) именно create database, а не только create table) 

   4.2.Удаление базы данных 

   4.3.Вывод содержимого таблиц 

   4.4.Очистка(частичная - одной, и полная - всех) таблиц 

   4.5.Добавление новых данных 

   4.6.Поиск по заранее выбранному (вами) текстовому не ключевому полю 

   4.7.Обновление кортежа 

   4.8.Удаление по заранее выбранному текстовому не ключевому полю 

   4.9.Удаление конкретной записи, выбранной пользователем 

   4.10.Все функции должны быть реализованы как хранимые процедуры.  

Из GUI вызывать только хранимые процедуры/функции. Выполнение произвольного SQL кода запрещено.  
В качестве отчёта от вас ожидается: описание вашей предметной области, схема бд(с пояснением в какой НФ она находится и почему), исходный код(лучше всего выкладывайте на любой source control server, например github, bitbucket и мне просто ссылку) и демонстрация работы вашего приложения на практике в виде видео с комментариями 
