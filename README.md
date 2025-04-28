# Task Service

## Описание
RESTful сервис для управления задачами. Разработан на основе стека Java 21, Spring Boot 3, PostgreSQL. Поддерживает CRUD-операции над задачами.

## Функциональность
- Создание задачи
- Получение списка задач
- Обновление задачи
- Удаление задачи

## Стек технологий
- Java 21
- Spring Boot 3
- Spring Data JPA
- PostgreSQL
- Docker
- MapStruct
- JUnit 5 + Mockito

## Быстрый старт

1. Клонировать репозиторий:
```bash
git clone https://github.com/zhelandovskiy-ka/task-service.git
```

2. Перейти в директорию проекта:
```bash
cd t1-task-service
```

3. Запустить проект через Docker:
```bash
docker-compose up --build  
```

4. Доступ к API:

GET /tasks

POST /tasks

PUT /tasks/{id}

DELETE /tasks/{id}

Примеры запросов

Получение всех задач:
```bash
curl --location 'localhost:8001/tasks'
```

Создание задачи:
```bash
curl --location 'localhost:8001/tasks' \
--header 'Content-Type: application/json' \
--data '{
  "tittle": "Task tittle",
  "description": "Task description",
  "status": "created",
  "userId": 1
}
'
```

Изменение задачи:
```bash
curl --location --request PUT 'localhost:8001/tasks/1' \
--header 'Content-Type: application/json' \
--data '{
  "tittle": "Task tittle 2",
  "description": "Task description 2",
  "status": "finished",
  "userId": 1
}'
```

Удаление задачи:
```bash
curl --location --request DELETE 'localhost:8001/tasks/1'
```
