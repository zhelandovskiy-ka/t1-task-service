package com.zhelandovskiy.t1_task_service.service.impl;

import com.zhelandovskiy.t1_task_service.config.kafka.KafkaProperties;
import com.zhelandovskiy.t1_task_service.dto.TaskCreateUpdateDto;
import com.zhelandovskiy.t1_task_service.dto.TaskDto;
import com.zhelandovskiy.t1_task_service.entity.TaskEntity;
import com.zhelandovskiy.t1_task_service.exception.TaskNotFoundException;
import com.zhelandovskiy.t1_task_service.kafka.KafkaClientProducer;
import com.zhelandovskiy.t1_task_service.mapper.TaskMapper;
import com.zhelandovskiy.t1_task_service.repository.TaskRepository;
import com.zhelandovskiy.t1_task_service.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final KafkaClientProducer kafkaClientProducer;
    private final KafkaProperties kafkaProperties;

    @Override
    public List<TaskDto> getAll() {
        return taskRepository.findAll().stream()
                .map(taskMapper::toDto)
                .toList();
    }

    @Override
    public TaskDto getById(Long id) {
        TaskEntity taskEntity = taskRepository.findById(id).orElseThrow(TaskNotFoundException::new);

        return taskMapper.toDto(taskEntity);
    }

    @Override
    public TaskDto create(TaskCreateUpdateDto taskDto) {
        TaskEntity taskEntity = taskMapper.createUpdateDtoToEntity(taskDto);

        return taskMapper.toDto(taskRepository.save(taskEntity));
    }

    @Override
    public TaskDto update(TaskCreateUpdateDto taskDto, Long id) {
        TaskEntity taskEntity = taskRepository.findById(id).orElseThrow(TaskNotFoundException::new);

        taskMapper.updateEntityFromDto(taskDto, taskEntity);

        TaskDto dto = taskMapper.toDto(taskRepository.save(taskEntity));

        kafkaClientProducer.sendTo(kafkaProperties.getTopic(), dto);

        return dto;
    }

    @Override
    public boolean delete(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new TaskNotFoundException();
        }

        taskRepository.deleteById(id);

        return true;
    }
}
