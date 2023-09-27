package com.suleman.todoapp.services;

import com.suleman.todoapp.web.TodoItem;

import java.util.List;
import java.util.Optional;

public interface TodoService {
    Optional<TodoItem> getTodoItem(Long id);

    List<TodoItem> getTodoItems();

    void removeTodoItem(Long id);

    TodoItem addTodoItem(TodoItem newTodoItem);

    TodoItem updateTodoItem(TodoItem todoItem);
}
