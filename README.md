# Todo Microservice

A small Spring Boot REST microservice for managing simple TODO items (CRUD).  
The project demonstrates a layered structure with a controller, service, JPA repository and tests.

Links to key code:
- Application entry: [`com.suleman.todoapp.TodoApplication`](src/main/java/com/suleman/todoapp/TodoApplication.java) — [src/main/java/com/suleman/todoapp/TodoApplication.java](src/main/java/com/suleman/todoapp/TodoApplication.java)  
- REST controller: [`com.suleman.todoapp.web.TodoController`](src/main/java/com/suleman/todoapp/web/TodoController.java) — [src/main/java/com/suleman/todoapp/web/TodoController.java](src/main/java/com/suleman/todoapp/web/TodoController.java)  
- Controller advice: [`com.suleman.todoapp.web.TodoItemNotFoundAdvice`](src/main/java/com/suleman/todoapp/web/TodoItemNotFoundAdvice.java) — [src/main/java/com/suleman/todoapp/web/TodoItemNotFoundAdvice.java](src/main/java/com/suleman/todoapp/web/TodoItemNotFoundAdvice.java)  
- Service API: [`com.suleman.todoapp.services.TodoService`](src/main/java/com/suleman/todoapp/services/TodoService.java) — [src/main/java/com/suleman/todoapp/services/TodoService.java](src/main/java/com/suleman/todoapp/services/TodoService.java)  
- Service impl: [`com.suleman.todoapp.services.TodoServiceImp`](src/main/java/com/suleman/todoapp/services/TodoServiceImp.java) — [src/main/java/com/suleman/todoapp/services/TodoServiceImp.java](src/main/java/com/suleman/todoapp/services/TodoServiceImp.java)  
- Repository: [`com.suleman.todoapp.data.TodoRepository`](src/main/java/com/suleman/todoapp/data/TodoRepository.java) — [src/main/java/com/suleman/todoapp/data/TodoRepository.java](src/main/java/com/suleman/todoapp/data/TodoRepository.java)  
- Domain model: [`com.suleman.todoapp.model.TodoItem`](src/main/java/com/suleman/todoapp/model/TodoItem.java) — [src/main/java/com/suleman/todoapp/model/TodoItem.java](src/main/java/com/suleman/todoapp/model/TodoItem.java)  
- Tests: integration & unit tests — [src/test/java/com/suleman/todoapp/TodoAppIntegrationTest.java](src/test/java/com/suleman/todoapp/TodoAppIntegrationTest.java), [src/test/java/com/suleman/todoapp/web/TodoControllerTest.java](src/test/java/com/suleman/todoapp/web/TodoControllerTest.java), [src/test/java/com/suleman/todoapp/services/TodoServiceImpTest.java](src/test/java/com/suleman/todoapp/services/TodoServiceImpTest.java), [src/test/java/com/suleman/todoapp/data/TodoRepositoryIntegrationTest.java](src/test/java/com/suleman/todoapp/data/TodoRepositoryIntegrationTest.java)

Requirements
- Java 21 (project configured in [pom.xml](pom.xml))
- Maven

Build
```sh
mvn -B package
```

Run (development)
```sh
mvn spring-boot:run
```

Run (jar)
```sh
java -jar target/todo-microservice-1.0-SNAPSHOT.jar
```

Run tests
```sh
mvn test
```

API (base: http://localhost:8080/api/todo)
- GET /api/todo — list all items  
- GET /api/todo/{id} — get item by id (404 if not found)  
- POST /api/todo — create new item (returns 201 and Location)  
- PUT /api/todo — update existing item (returns 404 if not found)  
- DELETE /api/todo/{id} — delete by id

Example curl (create)
```sh
curl -i -X POST -H "Content-Type: application/json" \
  -d '{"description":"Buy milk"}' http://localhost:8080/api/todo
```

Notes
- Uses in-memory H2 at runtime (configured by Spring Boot defaults).  
- Behavior for missing items is implemented by [`com.suleman.todoapp.services.TodoItemNotFoundException`](src/main/java/com/suleman/todoapp/services/TodoItemNotFoundException.java) — [src/main/java/com/suleman/todoapp/services/TodoItemNotFoundException.java](src/main/java/com/suleman/todoapp/services/TodoItemNotFoundException.java)

License
- BSD 2-Clause — [LICENSE](LICENSE)

