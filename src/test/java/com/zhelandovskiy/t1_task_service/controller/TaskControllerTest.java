package com.zhelandovskiy.t1_task_service.controller;

import com.zhelandovskiy.t1_task_service.config.kafka.KafkaProperties;
import com.zhelandovskiy.t1_task_service.dto.TaskCreateUpdateDto;
import com.zhelandovskiy.t1_task_service.entity.TaskEntity;
import com.zhelandovskiy.t1_task_service.kafka.KafkaClientConsumer;
import com.zhelandovskiy.t1_task_service.repository.TaskRepository;
import com.zhelandovskiy.t1_task_service.service.MailService;
import com.zhelandovskiy.t1_task_service.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TaskRepository taskRepository;

    @MockBean
    private KafkaClientConsumer kafkaClientConsumer;
    @MockBean
    private NotificationService notificationService;
    @MockBean
    private MailService mailService;

    private ObjectMapper objectMapper = new ObjectMapper();
    private TaskCreateUpdateDto taskCreateUpdateDto;
    private List<TaskEntity> createdTaskList;

    @BeforeEach
    void setUp() {
        taskRepository.deleteAll();

        TaskEntity taskEntity1 = new TaskEntity(null, "title1", "description1", "status1", 1L);
        TaskEntity taskEntity2 = new TaskEntity(null, "title2", "description2", "status2", 1L);
        TaskEntity taskEntity3 = new TaskEntity(null, "title3", "description3", "status3", 1L);

        createdTaskList = taskRepository.saveAll(Arrays.asList(taskEntity1, taskEntity2, taskEntity3));

        taskCreateUpdateDto = new TaskCreateUpdateDto("tittle1", "description1", "status1", 1L);
    }

    @Test
    @DisplayName("get all tasks")
    void getAllTask() throws Exception {
        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].title").value("title1"));
    }

    @Test
    @DisplayName("get task by id")
    void getTaskById() throws Exception {
        Long id = createdTaskList.get(0).getId();

        mockMvc.perform(get("/tasks/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("title1"));
    }

    @Test
    @DisplayName("not found get task by id")
    void getTaskByIdTaskNotFound() throws Exception {
        mockMvc.perform(get("/tasks/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("create task")
    void create() throws Exception {
        mockMvc.perform(post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(taskCreateUpdateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(taskCreateUpdateDto.getTitle()));
    }

    @Test
    @DisplayName("update task")
    void update() throws Exception {
        taskCreateUpdateDto.setTitle("title2");

        Long id = createdTaskList.get(0).getId();

        mockMvc.perform(put("/tasks/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(taskCreateUpdateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(taskCreateUpdateDto.getTitle()));
    }

    @Test
    @DisplayName("not found update task")
    void updateTaskNotFound() throws Exception {
        taskCreateUpdateDto.setTitle("title2");

        mockMvc.perform(put("/tasks/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(taskCreateUpdateDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("delete task")
    void deleteTask() throws Exception {
        Long id = createdTaskList.get(0).getId();

        mockMvc.perform(delete("/tasks/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));

    }

    @Test
    @DisplayName("not found delete task")
    void deleteTaskNotFound() throws Exception {
        mockMvc.perform(delete("/tasks/11"))
                .andExpect(status().isNotFound());

    }
}