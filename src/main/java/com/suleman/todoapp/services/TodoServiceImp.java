package com.suleman.todoapp.services;

import com.suleman.todoapp.data.TodoRepository;
import com.suleman.todoapp.model.TodoItem;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoServiceImp implements TodoService {

    private final TodoRepository todoRepository;

    public TodoServiceImp(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public TodoItem getTodoItem(Long id) throws TodoItemNotFoundException {
        return todoRepository
                .findById(id)
                .orElseThrow(() -> new TodoItemNotFoundException(id));
    }

    @Override
    public List<TodoItem> getTodoItems() {
        return todoRepository.findAll();
    }

    @Override
    public void removeTodoItem(Long id) {
        todoRepository.deleteById(id);
    }

    @Override
    public TodoItem addTodoItem(TodoItem newTodoItem) {
        return todoRepository.save(newTodoItem);
    }

    @Override
    public TodoItem updateTodoItem(TodoItem todoItem) throws TodoItemNotFoundException {
        if(!todoRepository.existsById(todoItem.getId())) {
            throw new TodoItemNotFoundException(todoItem.getId());
        }
        return todoRepository.save(todoItem);
    }
}
