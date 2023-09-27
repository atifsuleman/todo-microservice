package com.suleman.todoapp.web;

import com.suleman.todoapp.services.TodoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TodoController.class)
@DisplayName("the todo controller")
public class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TodoController todoController;

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
        todoItem.setId(200);
        todoItem.setDone(true);
        when(todoService.getTodoItem(200)).thenReturn(todoItem);

        ResultActions response = mockMvc.perform(
                get("/api/todo/200")
                        .contentType(MediaType.APPLICATION_JSON));

        response
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(equalTo(200))))
                .andExpect(jsonPath("$.description", is(equalTo("Buy the milk"))))
                .andExpect(jsonPath("$.done", is(equalTo(true))));
    }

}
