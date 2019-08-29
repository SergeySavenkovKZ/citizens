# citizens

Тестовое задание с использованием Spring Boot REST API

# 1) Конфигурация PostgreSql:

Создать базу данных и пользователя:

CREATE DATABASE test_database;
CREATE USER test_user WITH password 'qwerty';
GRANT ALL ON DATABASE test_database TO test_user;

Далее, под пользователем test_user выполнить команды из файла: 
citizens/src/main/java/com/project/citizens/domain/db_script.sql

# 2) Конфигурация проекта:

Открыть файл citizens/src/main/resources/application.properties и настроить следующие параметры:

spring.datasource.url=jdbc:postgresql://localhost:5432/test_database
spring.datasource.username=test_user
spring.datasource.password=qwerty

# 3) Сборка проекта:

maven package

# 4) Запуск на выполнение:

java — jar citizens-0.0.1-SNAPSHOT.jar

# 5) Открыть браузер и ввести команду:

http://localhost:9090/swagger-ui.html
