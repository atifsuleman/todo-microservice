package com.suleman.todoapp.data;

import com.suleman.todoapp.model.TodoItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@DisplayName("Todo repository")
public class TodoRepositoryIntegrationTest {

    @Autowired
    TodoRepository todoRepository;

    @Test
    @DisplayName("should save todo item entity")
    void shouldSaveTodoItemEntity() {
        var buyTheMilkTodoItem = new TodoItem("Buy the milk");
        buyTheMilkTodoItem.setDone(true);

        var savedTodoItem = todoRepository.save(buyTheMilkTodoItem);

        assertThat(savedTodoItem.getId()).isNotNull();
        assertThat(savedTodoItem.getDescription()).isEqualTo("Buy the milk");
        assertThat(savedTodoItem.isDone()).isTrue();
    }

    @Test
    @DisplayName("should not save todo item entity with null description")
    void shouldNotSaveTodoItemEntityWithNullDescription() {
        assertThrows(DataIntegrityViolationException.class, () -> todoRepository.save(new TodoItem()));
    }

    @Test
    @DisplayName("should not save todo item entity with null done")
    void shouldNotSaveTodoItemEntityWithNullDone() {
        var todoItemWithDoneSetToNull = new TodoItem("Wash the car");
        todoItemWithDoneSetToNull.setDone(null);
        assertThrows(DataIntegrityViolationException.class, () -> todoRepository.save(todoItemWithDoneSetToNull));
    }
}
