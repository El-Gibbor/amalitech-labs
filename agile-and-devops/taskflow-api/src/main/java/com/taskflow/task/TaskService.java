package com.taskflow.task;

import com.taskflow.task.dto.CreateTaskRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task createTask(CreateTaskRequest request) {
        Task task = new Task(request.getTitle(), request.getDescription(), request.getPriority());
        return taskRepository.save(task);
    }

    public List<Task> listTasks() {
        return taskRepository.findAll();
    }

    public Task getTask(Long id) {
        // Throwing keeps the controller a thin pass-through; ApiExceptionHandler maps this to 404.
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }
}
