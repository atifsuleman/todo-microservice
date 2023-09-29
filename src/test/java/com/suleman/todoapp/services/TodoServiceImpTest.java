package com.suleman.todoapp.services;

import com.suleman.todoapp.data.TodoRepository;
import com.suleman.todoapp.model.TodoItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Todo service")
public class TodoServiceImpTest {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoServiceImp todoService;

    @Test
    @DisplayName("should exists")
    void shouldExists() {
        assertThat(todoService).isNotNull();
    }

    @Test
    @DisplayName("should return given id todo item")
    void shouldReturnGivenIdTodoItem() throws TodoItemNotFoundException {
        TodoItem buyMilkTodoItem = new TodoItem("Buy milk");
        buyMilkTodoItem.setId(200L);
        when(todoRepository.findById(200L)).thenReturn(Optional.of(buyMilkTodoItem));

        var todoItemForId200 = todoService.getTodoItem(200L);

        assertThat(todoItemForId200).isEqualTo(buyMilkTodoItem);
    }

    @Test
    @DisplayName("should throw error for given id of todo item that does not exist")
    void shouldThrowErrorForGivenIdOfTodoItemThatDoesNotExist() {
        when(todoRepository.findById(488L)).thenReturn(Optional.empty());

        var todoItemNotFoundException = assertThrows(TodoItemNotFoundException.class, () -> todoService.getTodoItem(488L));

        assertThat(todoItemNotFoundException).hasMessage("todo item 488 could not be found.");
    }

    @Test
    @DisplayName("should return all todo items")
    void shouldReturnAllTodoItems() {
        TodoItem buyTheMilkTodoItem = new TodoItem("Buy the milk");
        buyTheMilkTodoItem.setId(200L);
        buyTheMilkTodoItem.setDone(true);

        TodoItem readBookTodoItem = new TodoItem("Read the book Passage");
        readBookTodoItem.setId(201L);

        TodoItem washCarTodoItem = new TodoItem("Wash the car");
        washCarTodoItem.setId(202L);
        washCarTodoItem.setDone(true);

        when(todoRepository.findAll()).thenReturn(List.of(buyTheMilkTodoItem, readBookTodoItem, washCarTodoItem));

        var allTodoItems = todoService.getTodoItems();

        assertThat(allTodoItems).containsExactly(buyTheMilkTodoItem, readBookTodoItem, washCarTodoItem);
    }

    @Test
    @DisplayName("should remove given id todo item")
    void shouldRemoveGivenIdTodoItem() {
        todoService.removeTodoItem(333L);

        verify(todoRepository).deleteById(333L);
    }

    @Test
    @DisplayName("should add new todo item")
    void shouldAddNewTodoItem() {
        TodoItem buyTheMilkTodoItem = new TodoItem("Buy the milk");
        when(todoRepository.save(buyTheMilkTodoItem)).thenReturn(buyTheMilkTodoItem);

        var theSavedTodoItem = todoService.addTodoItem(buyTheMilkTodoItem);

        assertThat(theSavedTodoItem).isEqualTo(buyTheMilkTodoItem);
    }

    @Test
    @DisplayName("should update the todo item")
    void shouldUpdateTheTodoItem() throws TodoItemNotFoundException {
        TodoItem buyTheMilkTodoItem = new TodoItem("Buy the milk");
        buyTheMilkTodoItem.setId(200L);
        buyTheMilkTodoItem.setDone(true);
        when(todoRepository.existsById(200L)).thenReturn(true);
        when(todoRepository.save(buyTheMilkTodoItem)).thenReturn(buyTheMilkTodoItem);

        var theUpdatedTodoItem = todoService.updateTodoItem(buyTheMilkTodoItem);

        assertThat(theUpdatedTodoItem).isEqualTo(buyTheMilkTodoItem);
    }

    @Test
    @DisplayName("should throw error when updating a todo item that does not exist")
    void shouldThrowErrorWhenUpdatingATodoItemThatDoesNotExist() {
        when(todoRepository.existsById(488L)).thenReturn(false);
        TodoItem todoItemThatDoesNotExist = new TodoItem("Todo Item That Does Not Exist");
        todoItemThatDoesNotExist.setId(488L);
        todoItemThatDoesNotExist.setDone(true);

        var todoItemNotFoundException = assertThrows(TodoItemNotFoundException.class, () -> todoService.updateTodoItem(todoItemThatDoesNotExist));

        assertThat(todoItemNotFoundException).hasMessage("todo item 488 could not be found.");
    }
}
