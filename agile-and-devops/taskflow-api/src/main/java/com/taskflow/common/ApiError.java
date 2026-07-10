package com.taskflow.common;

import java.util.List;

public class ApiError {

    private final String message;
    private final List<String> details;

    public ApiError(String message, List<String> details) {
        this.message = message;
        this.details = details;
    }

    public String getMessage() {
        return message;
    }

    public List<String> getDetails() {
        return details;
    }
}
