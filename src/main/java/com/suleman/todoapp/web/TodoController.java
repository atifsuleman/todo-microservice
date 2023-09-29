package com.suleman.todoapp.web;

import com.suleman.todoapp.model.TodoItem;
import com.suleman.todoapp.services.TodoItemNotFoundException;
import com.suleman.todoapp.services.TodoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/todo")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("/{id}")
    public TodoItem oneTodoItem(@PathVariable  Long id) throws TodoItemNotFoundException {
        return todoService.getTodoItem(id);
    }

    @GetMapping
    public List<TodoItem> allTodoItems() {
        return todoService.getTodoItems();
    }

    @DeleteMapping("/{id}")
    public void deleteTodoItem(@PathVariable  Long id) {
        todoService.removeTodoItem(id);
    }

    @PostMapping
    public ResponseEntity<TodoItem> newTodoItem(@RequestBody TodoItem newTodoItem) {
        TodoItem addedTodoItem = todoService.addTodoItem(new TodoItem(newTodoItem.getDescription()));
        return ResponseEntity
                .created(URI.create("/api/todo/%d".formatted(addedTodoItem.getId())))
                .body(addedTodoItem);
    }

    @PutMapping
    public TodoItem updateTodoItem(@RequestBody TodoItem updateTodoItem) throws TodoItemNotFoundException {
        return todoService.updateTodoItem(updateTodoItem);
    }

}
