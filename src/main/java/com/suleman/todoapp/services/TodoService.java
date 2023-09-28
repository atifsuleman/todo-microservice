package com.suleman.todoapp.services;

import com.suleman.todoapp.model.TodoItem;

import java.util.List;

public interface TodoService {
    TodoItem getTodoItem(Long id) throws TodoItemNotFoundException;

    List<TodoItem> getTodoItems();

    void removeTodoItem(Long id);

    TodoItem addTodoItem(TodoItem newTodoItem);

    TodoItem updateTodoItem(TodoItem todoItem) throws TodoItemNotFoundException;
}
