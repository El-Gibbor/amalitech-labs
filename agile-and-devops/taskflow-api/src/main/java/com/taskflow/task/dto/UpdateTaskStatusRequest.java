package com.taskflow.task.dto;

import com.taskflow.task.TaskStatus;

import javax.validation.constraints.NotNull;

public class UpdateTaskStatusRequest {

    @NotNull(message = "status must not be null")
    private TaskStatus status;

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }
}
