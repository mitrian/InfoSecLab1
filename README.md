# Работа 1: Разработка защищенного REST API с

## Эндпоинты

### Методы для аутентификации

#### Регистрация пользователя

```http
POST /auth/register
Content-Type: application/json

{
  "username": "username_example",
  "password": "password_example"
}
```
##### Ответ:

You have successfully registered, your username: username_example

#### Аутентификация пользователя
```http
POST /auth/login
Content-Type: application/json

{
  "username": "username_example",
  "password": "username_example"
}
````
##### Ответ:

eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VybmFtZV9leGFtcGxlIiwiaWF0IjoxNzYyMjg1NTUwLCJleHAiOjE3NjIyODU5MTB9.UX7353_voZAohfyykJas7mWJ9Rwo_wi9yyQNS_y1cV4

### Методы доступные только авторизованным пользователям

#### Добавление предмета

```http
POST /api/data
Content-Type: application/json
Authorization: Bearer {jwt-token}

{
    "name": "item_example",
    "price": "1033"
}
```
##### Ответ:

Item item_example was created

#### Получение списка всех предметов

```http
GET /api/data
Content-Type: application/json
Authorization: Bearer {jwt-token}
````
#### Ответ:

```json
[
  {
    "id": 1,
    "name": "waka",
    "price": 1000
  },
  {
    "id": 2,
    "name": "hqd",
    "price": 1
  },
  {
    "id": 3,
    "name": "iqos",
    "price": 3000
  },
  {
    "id": 4,
    "name": "item_example",
    "price": 1033
  }
]
```

## Меры защиты

### 1. Защита от SQL-инъекций
- Все запросы к базе данных через **Spring Data JPA / Hibernate**.
- Параметризованные выражения (Prepared Statements) используются автоматически.

```Java
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
```

### 2. Защита от XSS

- **HtmlUtils.htmlEscape()** используется для экранирования специальных символов в теле ответа
```Java
public ItemResponseDTO addItem(ItemRequestDTO itemRequestDTO) {
    validateItemRequest(itemRequestDTO);

    Item item = new Item();
    item.setName(HtmlUtils.htmlEscape(itemRequestDTO.name().trim()));
    item.setPrice(itemRequestDTO.price());

    Item savedItem = itemRepository.save(item);

    return mapToResponseDTO(savedItem);
}
```
- Пример запроса:
```json
{
    "name": "<script>alert(\"Hello\")</script>",
    "price": "1044"
}
```
- Пример ответа: 
```
Item &lt;script&gt;alert(&quot;Hello&quot;)&lt;/script&gt; was created
```

### 3. Аутентификация по JWT

- **JWT токены**: HS256 с 256-битным секретным ключом для безопасной передачи данных аутентификации
- **Stateless подход**: не требуется хранение сессий на сервере, уменьшая нагрузку и упрощая масштабирование
- **Валидация**: Проверка подписи и срока действия токенов при каждом запросе к защищенным эндпоинтам
- **Middleware**: JwtAuthenticationFilter проверяет наличие и валидность токена в заголовке Authorization

### 4. Статический анализ и проверка зависимостей
- SpotBugs для выявления потенциальных уязвимостей / багов коде
- OWASP Dependency-Check для анализа зависимостей на известные CVE

## Скриншоты отчетов

### SAST

![SAST review](./res/images/spotbugs.png)

### SCA

![SCA review](./res/images/sca.png)
