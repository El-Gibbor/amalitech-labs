package com.taskflow.task.dto;

import com.taskflow.task.Priority;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CreateTaskRequest {

    @NotBlank(message = "title must not be blank")
    private String title;

    private String description;

    @NotNull(message = "priority must not be null")
    private Priority priority;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }
}
