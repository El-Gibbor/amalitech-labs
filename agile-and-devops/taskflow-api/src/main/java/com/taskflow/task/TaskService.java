package com.taskflow.task;

import com.taskflow.task.dto.CreateTaskRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private static final Logger log = LoggerFactory.getLogger(TaskService.class);

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task createTask(CreateTaskRequest request) {
        Task task = new Task(request.getTitle(), request.getDescription(), request.getPriority());
        Task saved = taskRepository.save(task);
        log.info("Created task id={} title=\"{}\"", saved.getId(), saved.getTitle());
        return saved;
    }

    public List<Task> listTasks() {
        return taskRepository.findAll();
    }

    public Task getTask(Long id) {
        // Throwing keeps the controller a thin pass-through; ApiExceptionHandler maps this to 404.
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    public Task updateStatus(Long id, TaskStatus status) {
        Task task = getTask(id);
        task.setStatus(status);
        Task saved = taskRepository.save(task);
        log.info("Updated task id={} status={}", saved.getId(), saved.getStatus());
        return saved;
    }
}
