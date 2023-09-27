package com.suleman.todoapp.web;

import com.suleman.todoapp.services.TodoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("/api/todo/{id}")
    public TodoItem oneTodoItem(@PathVariable  Long id) throws TodoItemNotFoundException {
        return todoService
                .getTodoItem(id)
                .orElseThrow(() -> new TodoItemNotFoundException(id));
    }

    @GetMapping("/api/todo")
    public List<TodoItem> allTodoItems() {
        return todoService.getTodoItems();
    }

    @DeleteMapping("/api/todo/{id}")
    public void deleteTodoItem(@PathVariable  Long id) {
        todoService.removeTodoItem(id);
    }

    @PostMapping("/api/todo")
    public ResponseEntity<TodoItem> newTodoItem(@RequestBody TodoItem newTodoItem) {
        TodoItem addedTodoItem = todoService.addTodoItem(new TodoItem(newTodoItem.getDescription()));
        return ResponseEntity
                .created(URI.create("/api/todo/%d".formatted(addedTodoItem.getId())))
                .body(addedTodoItem);
    }

    @PutMapping("/api/todo")
    public TodoItem updateTodoItem(@RequestBody TodoItem updateTodoItem) throws TodoItemNotFoundException {
        return todoService.updateTodoItem(updateTodoItem);
    }

}
