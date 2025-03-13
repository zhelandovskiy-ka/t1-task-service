package com.zhelandovskiy.t1_task_service.service.impl;

import com.zhelandovskiy.t1_task_service.config.kafka.KafkaProperties;
import com.zhelandovskiy.t1_task_service.dto.TaskCreateUpdateDto;
import com.zhelandovskiy.t1_task_service.dto.TaskDto;
import com.zhelandovskiy.t1_task_service.entity.TaskEntity;
import com.zhelandovskiy.t1_task_service.exception.TaskNotFoundException;
import com.zhelandovskiy.t1_task_service.kafka.KafkaClientProducer;
import com.zhelandovskiy.t1_task_service.mapper.TaskMapper;
import com.zhelandovskiy.t1_task_service.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;
    @Mock
    private TaskMapper taskMapper;
    @Mock
    private KafkaProperties kafkaProperties;
    @Mock
    private KafkaClientProducer kafkaClientProducer;

    @InjectMocks
    private TaskServiceImpl taskService;

    private List<TaskDto> taskList;
    private TaskEntity taskEntity;
    private TaskDto taskDto;
    private TaskCreateUpdateDto taskCreateUpdateDto;

    @BeforeEach
    public void init() {
        taskList = new ArrayList<>();
        taskList.add(new TaskDto(1L, "task1", "description1", "status1", 2L));
        taskList.add(new TaskDto(2L, "task2", "description2", "status2", 3L));

        taskEntity = new TaskEntity(1L, "task1", "description1", "status1", 2L);
        taskDto = new TaskDto(1L, "task1", "description1", "status1", 2L);
        taskCreateUpdateDto = new TaskCreateUpdateDto("task1", "description1", "status1", 2L);
    }

    @Test
    @DisplayName("getting all tasks")
    public void getAll() {
        when(taskService.getAll()).thenReturn(taskList);

        List<TaskDto> actualList = taskService.getAll();

        assertIterableEquals(taskList, actualList);
    }

    @Test
    @DisplayName("getting task by Id")
    public void getById() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(taskEntity));
        when(taskService.getById(1L)).thenReturn(taskDto);

        TaskDto result = taskService.getById(1L);

        assertEquals(taskDto, result);
    }

    @Test
    @DisplayName("getting task by Id exception")
    public void getByIdException() {
        assertThrows(TaskNotFoundException.class, () -> taskService.getById(1L));
    }

    @Test
    @DisplayName("create task")
    public void create() {
        when(taskMapper.createUpdateDtoToEntity(taskCreateUpdateDto)).thenReturn(taskEntity);
        when(taskRepository.save(taskEntity)).thenReturn(taskEntity);
        when(taskMapper.toDto(taskEntity)).thenReturn(taskDto);

        TaskDto result = taskService.create(taskCreateUpdateDto);

        assertEquals(taskDto, result);
    }

    @Test
    @DisplayName("update task")
    public void update() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(taskEntity));
        when(taskRepository.save(taskEntity)).thenReturn(taskEntity);
        when(taskMapper.toDto(taskEntity)).thenReturn(taskDto);
        when(kafkaProperties.getTopic()).thenReturn("test-topic");
        doNothing().when(kafkaClientProducer).sendTo(anyString(), any());

        TaskDto result = taskService.update(taskCreateUpdateDto, 1L);

        assertNotNull(result);
        assertEquals(taskDto, result);
    }


    @Test
    @DisplayName("update task exception")
    void updateException() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.update(taskCreateUpdateDto, 1L));
    }

    @Test
    @DisplayName("delete task")
    void delete() {
        when(taskRepository.existsById(1L)).thenReturn(true);
        doNothing().when(taskRepository).deleteById(1L);

        boolean result = taskService.delete(1L);

        assertTrue(result);
    }

    @Test
    @DisplayName("delete task exception")
    void deleteException() {
        assertThrows(TaskNotFoundException.class, () -> taskService.delete(1L));
    }
}