package com.suleman.todoapp.services;

import com.suleman.todoapp.web.TodoItem;
import com.suleman.todoapp.web.TodoItemNotFoundException;

import java.util.List;
import java.util.Optional;

public interface TodoService {
    TodoItem getTodoItem(Long id) throws TodoItemNotFoundException;

    List<TodoItem> getTodoItems();

    void removeTodoItem(Long id);

    TodoItem addTodoItem(TodoItem newTodoItem);

    TodoItem updateTodoItem(TodoItem todoItem) throws TodoItemNotFoundException;
}
