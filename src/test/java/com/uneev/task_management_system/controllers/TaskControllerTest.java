package com.uneev.task_management_system.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uneev.task_management_system.dto.TaskCreationDto;
import com.uneev.task_management_system.services.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskControllerImpl taskController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;


    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void shouldNotCreateTaskAndReturnStatus400BadRequest() throws Exception {
        TaskCreationDto taskCreationDto = new TaskCreationDto(
                "Title",
                "Description",
                "someinvalid@m@a@i@l@.r@.@u."
        );
        String taskJson = objectMapper.writeValueAsString(taskCreationDto);

        mockMvc.perform(post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(taskJson)
        ).andExpect(status().isBadRequest());
    }
}
