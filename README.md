Конечно, братан. Вот человечный README без примеров запросов, с акцентом на запуск и документацию.

---


# Yandex Forms Backend

Упрощённая копия бэкенда Яндекс Форм.  
Позволяет регистрироваться, создавать формы с вопросами (текст, радио, чекбоксы), отправлять ответы и управлять своими формами.

---

## Что умеет

- Регистрация и вход по JWT.
- Создание, просмотр, изменение и удаление форм.
- Поддержка трёх типов вопросов: текст, радио, чекбокс.
- Отправка ответов на формы.
- Получение списка своих форм с фильтрацией, сортировкой и пагинацией.
- Swagger UI для тестирования и документации.
- Готовый Docker Compose для поднятия базы и приложения.

---

## Технологии

Java 17, Spring Boot 3, Spring Security, JWT, Spring Data JPA, PostgreSQL, MapStruct, Lombok, Swagger (springdoc-openapi), Maven.

---

## Запуск

### Через IDE (локально)

1. Запустите PostgreSQL (например, через Docker):
   ```bash
   docker run --name yandex-forms-postgres \
     -e POSTGRES_DB=yandex_forms \
     -e POSTGRES_USER=postgres \
     -e POSTGRES_PASSWORD=postgres \
     -p 5432:5432 -d postgres:15
   ```

2. Склонируйте проект и откройте в IntelliJ IDEA.

3. Убедитесь, что в `src/main/resources/application.yml` указаны правильные данные для подключения к базе (хост, порт, имя БД, логин, пароль).  
   Пример настройки:
   ```yaml
   spring:
     datasource:
       url: jdbc:postgresql://localhost:5432/yandex_forms
       username: postgres
       password: postgres
   app:
     jwt:
       secret: mySecretKeyWithAtLeast32CharactersLong!
       expiration-ms: 86400000
   ```

4. Запустите класс `YandexFromApplication`.

Приложение будет доступно по адресу: [http://localhost:8080](http://localhost:8080)

---

### Через Docker Compose

1. Убедитесь, что в корне проекта есть файлы `Dockerfile` и `docker-compose.yml`.
2. Выполните:
   ```bash
   docker-compose up -d --build
   ```
3. Приложение поднимется вместе с PostgreSQL.

---

## Документация API

Swagger UI доступен по адресу:  
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

Там можно посмотреть все эндпоинты, схемы данных и выполнить тестовые запросы.

---

## Postman коллекция

В корне репозитория лежит файл `YandexForms.postman_collection.json`.  
Импортируйте его в Postman – коллекция уже настроена на автоматическое сохранение JWT-токена после входа.  
Вам останется только последовательно выполнить запросы.

---

## Структура проекта (основные пакеты)

- `controllers` – REST-контроллеры
- `services` – бизнес-логика
- `repositories` – JPA-репозитории
- `entities` – сущности базы данных
- `dto` – объекты передачи данных
- `mappers` – мапперы MapStruct
- `security` – JWT фильтр, конфигурация безопасности, `UserDetailsService`
- `exceptions` – кастомные исключения и глобальный обработчик ошибок


