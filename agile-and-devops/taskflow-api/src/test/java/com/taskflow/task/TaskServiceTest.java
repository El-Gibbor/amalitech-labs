package com.taskflow.task;

import com.taskflow.task.dto.CreateTaskRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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

    @Test
    void listTasksReturnsAllTasksFromRepository() {
        TaskService taskService = new TaskService(taskRepository);
        Task existing = new Task("Existing task", "Already recorded", Priority.LOW);

        when(taskRepository.findAll()).thenReturn(Collections.singletonList(existing));

        List<Task> tasks = taskService.listTasks();

        assertThat(tasks).hasSize(1);
        assertThat(tasks.get(0).getTitle()).isEqualTo("Existing task");
    }

    @Test
    void listTasksReturnsEmptyListWhenNoneExist() {
        TaskService taskService = new TaskService(taskRepository);

        when(taskRepository.findAll()).thenReturn(Collections.emptyList());

        List<Task> tasks = taskService.listTasks();

        assertThat(tasks).isEmpty();
    }

    @Test
    void getTaskReturnsTaskWhenFound() {
        TaskService taskService = new TaskService(taskRepository);
        Task existing = new Task("Existing task", "Already recorded", Priority.LOW);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(existing));

        Task task = taskService.getTask(1L);

        assertThat(task.getTitle()).isEqualTo("Existing task");
    }

    @Test
    void getTaskThrowsWhenNotFound() {
        TaskService taskService = new TaskService(taskRepository);

        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> taskService.getTask(1L))
                .isInstanceOf(TaskNotFoundException.class);
    }

    @Test
    void updateStatusChangesStatusAndPersists() {
        TaskService taskService = new TaskService(taskRepository);
        Task existing = new Task("Existing task", "Already recorded", Priority.LOW);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(taskRepository.save(existing)).thenReturn(existing);

        Task updated = taskService.updateStatus(1L, TaskStatus.IN_PROGRESS);

        assertThat(updated.getStatus()).isEqualTo(TaskStatus.IN_PROGRESS);
    }

    @Test
    void updateStatusThrowsWhenTaskNotFound() {
        TaskService taskService = new TaskService(taskRepository);

        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> taskService.updateStatus(1L, TaskStatus.DONE))
                .isInstanceOf(TaskNotFoundException.class);
    }
}
