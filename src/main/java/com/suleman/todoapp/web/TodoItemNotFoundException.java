package com.suleman.todoapp.web;

public class TodoItemNotFoundException extends RuntimeException {
    public TodoItemNotFoundException(int id) {
        super("todo item %d could not be found.".formatted(id));
    }
}
