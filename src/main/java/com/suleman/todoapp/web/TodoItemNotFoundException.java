package com.suleman.todoapp.web;

public class TodoItemNotFoundException extends Exception {
    public TodoItemNotFoundException(Long id) {
        super("todo item %d could not be found.".formatted(id));
    }
}
