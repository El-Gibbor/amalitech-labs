package com.taskflow.task;

import com.taskflow.task.dto.CreateTaskRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Test
    void createTaskPersistsTaskWithDefaultTodoStatus() {
        TaskService taskService = new TaskService(taskRepository);

        CreateTaskRequest request = new CreateTaskRequest();
        request.setTitle("Write report");
        request.setDescription("Quarterly summary");
        request.setPriority(Priority.HIGH);

        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Task created = taskService.createTask(request);

        ArgumentCaptor<Task> captor = ArgumentCaptor.forClass(Task.class);
        org.mockito.Mockito.verify(taskRepository).save(captor.capture());

        assertThat(captor.getValue().getTitle()).isEqualTo("Write report");
        assertThat(captor.getValue().getDescription()).isEqualTo("Quarterly summary");
        assertThat(captor.getValue().getPriority()).isEqualTo(Priority.HIGH);
        assertThat(captor.getValue().getStatus()).isEqualTo(TaskStatus.TODO);
        assertThat(created.getStatus()).isEqualTo(TaskStatus.TODO);
    }
}
