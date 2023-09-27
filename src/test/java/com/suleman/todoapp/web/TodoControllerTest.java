package com.suleman.todoapp.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.suleman.todoapp.services.TodoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = TodoController.class)
@DisplayName("the todo controller")
public class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TodoController todoController;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TodoService todoService;

    @Test
    @DisplayName("should exists")
    void shouldExists() {
        assertThat(todoController).isNotNull();
    }

    @Test
    @DisplayName("should return given id todo item")
    void shouldReturnGivenIdTodoItem() throws Exception {
        TodoItem todoItem = new TodoItem("Buy the milk");
        todoItem.setId(200L);
        todoItem.setDone(true);
        when(todoService.getTodoItem(200L)).thenReturn(Optional.of(todoItem));

        ResultActions response = mockMvc.perform(
                get("/api/todo/200")
                        .contentType(MediaType.APPLICATION_JSON));

        response
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(equalTo(200))))
                .andExpect(jsonPath("$.description", is(equalTo("Buy the milk"))))
                .andExpect(jsonPath("$.done", is(equalTo(true))));
    }

    @Test
    @DisplayName("should return not found error when the given id todo item doesn't exist")
    void shouldReturnNotFoundErrorWhenTheGivenIdTodoItemDoesnTExist() throws Exception {
        when(todoService.getTodoItem(488L)).thenReturn(Optional.empty());

        ResultActions response = mockMvc.perform(
                get("/api/todo/488")
                        .contentType(MediaType.APPLICATION_JSON));

        response
                .andExpect(status().isNotFound())
                .andExpect(content().string("todo item 488 could not be found."));

    }

    @Test
    @DisplayName("should return all todo items")
    void shouldReturnAllTodoItems() throws Exception {
        TodoItem todoItem1 = new TodoItem("Buy the milk");
        todoItem1.setId(200L);
        todoItem1.setDone(true);

        TodoItem todoItem2 = new TodoItem("Read the book Passage");
        todoItem2.setId(201L);

        TodoItem todoItem3 = new TodoItem("Wash the car");
        todoItem3.setId(202L);
        todoItem3.setDone(true);

        when(todoService.getTodoItems()).thenReturn(List.of(todoItem1, todoItem2, todoItem3));

        ResultActions response = mockMvc.perform(
                get("/api/todo")
                        .contentType(MediaType.APPLICATION_JSON));

        response
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(equalTo(3))))
                .andExpect(jsonPath("$.[*].id", hasItems(200, 201, 202)))
                .andExpect(jsonPath("$.[*].description", hasItems("Buy the milk", "Read the book Passage", "Wash the car")))
                .andExpect(jsonPath("$.[*].done", hasItems(true, false, true)));
    }

    @Test
    @DisplayName("should delete given id todo item")
    void shouldDeleteGivenIdTodoItem() throws Exception {
        ResultActions response = mockMvc.perform(
                delete("/api/todo/200")
                        .contentType(MediaType.APPLICATION_JSON));

        response
                .andExpect(status().isOk());
        verify(todoService).removeTodoItem(200L);
    }

    @Test
    @DisplayName("should create new todo item")
    void shouldCreateNewTodoItem() throws Exception {
        TodoItem newTodoItem = new TodoItem("Buy the milk");
        when(todoService.addTodoItem(newTodoItem)).thenAnswer(invocationOnMock -> {
            TodoItem item = invocationOnMock.getArgument(0);
            item.setId(200L);
            return item;
        });

        ResultActions response = mockMvc.perform(
                post("/api/todo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newTodoItem))
        );

        response
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", is(equalTo("/api/todo/200"))))
                .andExpect(jsonPath("$.id", is(equalTo(200))))
                .andExpect(jsonPath("$.description", is(equalTo("Buy the milk"))))
                .andExpect(jsonPath("$.done", is(equalTo(false))));
    }

}
