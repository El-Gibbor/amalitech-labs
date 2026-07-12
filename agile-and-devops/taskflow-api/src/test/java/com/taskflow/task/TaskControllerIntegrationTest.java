package com.taskflow.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.taskflow.task.dto.CreateTaskRequest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class TaskControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createTaskWithValidPayloadReturnsCreatedTaskWithTodoStatus() throws Exception {
        CreateTaskRequest request = new CreateTaskRequest();
        request.setTitle("Prepare sprint review");
        request.setDescription("Summarize delivered stories");
        request.setPriority(Priority.MEDIUM);

        mockMvc.perform(post("/api/tasks")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.title").value("Prepare sprint review"))
                .andExpect(jsonPath("$.status").value("TODO"))
                .andExpect(jsonPath("$.priority").value("MEDIUM"));
    }

    @Test
    void createTaskWithBlankTitleIsRejected() throws Exception {
        CreateTaskRequest request = new CreateTaskRequest();
        request.setTitle(" ");
        request.setPriority(Priority.LOW);

        mockMvc.perform(post("/api/tasks")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void listTasksReturnsEmptyListWhenNoneExist() throws Exception {
        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void listTasksReturnsPreviouslyCreatedTasks() throws Exception {
        CreateTaskRequest request = new CreateTaskRequest();
        request.setTitle("Draft agenda");
        request.setPriority(Priority.LOW);

        mockMvc.perform(post("/api/tasks")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)));

        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].title").value("Draft agenda"));
    }

    @Test
    void getTaskByIdReturnsTaskWhenFound() throws Exception {
        CreateTaskRequest request = new CreateTaskRequest();
        request.setTitle("Draft agenda");
        request.setPriority(Priority.LOW);

        String response = mockMvc.perform(post("/api/tasks")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andReturn().getResponse().getContentAsString();

        Long id = objectMapper.readTree(response).get("id").asLong();

        mockMvc.perform(get("/api/tasks/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Draft agenda"));
    }

    @Test
    void getTaskByIdReturnsNotFoundWhenMissing() throws Exception {
        mockMvc.perform(get("/api/tasks/{id}", 999))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateStatusWithValidValueReturnsUpdatedTask() throws Exception {
        CreateTaskRequest createRequest = new CreateTaskRequest();
        createRequest.setTitle("Draft agenda");
        createRequest.setPriority(Priority.LOW);

        String response = mockMvc.perform(post("/api/tasks")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(createRequest)))
                .andReturn().getResponse().getContentAsString();

        Long id = objectMapper.readTree(response).get("id").asLong();

        mockMvc.perform(patch("/api/tasks/{id}/status", id)
                        .contentType("application/json")
                        .content("{\"status\":\"IN_PROGRESS\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("IN_PROGRESS"));
    }

    @Test
    void updateStatusWithInvalidValueIsRejectedAndStatusUnchanged() throws Exception {
        CreateTaskRequest createRequest = new CreateTaskRequest();
        createRequest.setTitle("Draft agenda");
        createRequest.setPriority(Priority.LOW);

        String response = mockMvc.perform(post("/api/tasks")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(createRequest)))
                .andReturn().getResponse().getContentAsString();

        Long id = objectMapper.readTree(response).get("id").asLong();

        mockMvc.perform(patch("/api/tasks/{id}/status", id)
                        .contentType("application/json")
                        .content("{\"status\":\"BOGUS\"}"))
                .andExpect(status().isBadRequest());

        mockMvc.perform(get("/api/tasks/{id}", id))
                .andExpect(jsonPath("$.status").value("TODO"));
    }

    @Test
    void updateStatusForMissingTaskReturnsNotFound() throws Exception {
        mockMvc.perform(patch("/api/tasks/{id}/status", 999)
                        .contentType("application/json")
                        .content("{\"status\":\"DONE\"}"))
                .andExpect(status().isNotFound());
    }
}
