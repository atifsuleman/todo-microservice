package com.suleman.todoapp.services;

import com.suleman.todoapp.web.TodoItem;

import java.util.List;

public interface TodoService {
    TodoItem getTodoItem(int id);

    List<TodoItem> getTodoItems();
}
