package com.suleman.todoapp.web;

import com.suleman.todoapp.services.TodoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("/api/todo/{id}")
    public TodoItem oneTodoItem(@PathVariable  Long id) {
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
    public TodoItem newTodoItem(@RequestBody TodoItem newTodoItem) {
        return todoService.addTodoItem(new TodoItem(newTodoItem.getDescription()));
    }

}
