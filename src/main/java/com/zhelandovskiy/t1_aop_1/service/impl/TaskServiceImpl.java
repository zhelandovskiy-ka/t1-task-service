package com.zhelandovskiy.t1_aop_1.service.impl;

import com.zhelandovskiy.t1_aop_1.aop.annotation.CalculateRecordsLog;
import com.zhelandovskiy.t1_aop_1.aop.annotation.TimeMetric;
import com.zhelandovskiy.t1_aop_1.dto.TaskCreateUpdateDto;
import com.zhelandovskiy.t1_aop_1.dto.TaskDto;
import com.zhelandovskiy.t1_aop_1.entity.TaskEntity;
import com.zhelandovskiy.t1_aop_1.exception.TaskNotFoundException;
import com.zhelandovskiy.t1_aop_1.mapper.TaskMapper;
import com.zhelandovskiy.t1_aop_1.repository.TaskRepository;
import com.zhelandovskiy.t1_aop_1.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@TimeMetric
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Override
    @CalculateRecordsLog
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

        return taskMapper.toDto(taskRepository.save(taskEntity));
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
