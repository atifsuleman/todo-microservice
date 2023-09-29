package com.suleman.todoapp;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DisplayName("Todo app")
public class TodoAppIntegrationTest {

    @Autowired
    private TodoApplication todoApplication;

    @Test
    @DisplayName("should load the context")
    void shouldLoadTheContext() {
        assertThat(todoApplication).isNotNull();
    }

}
