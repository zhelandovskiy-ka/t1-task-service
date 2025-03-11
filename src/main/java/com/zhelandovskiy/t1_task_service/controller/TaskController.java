package com.zhelandovskiy.t1_task_service.controller;

import com.zhelandovskiy.annotation.HttpLog;
import com.zhelandovskiy.annotation.TimeMetric;
import com.zhelandovskiy.t1_task_service.dto.TaskCreateUpdateDto;
import com.zhelandovskiy.t1_task_service.dto.TaskDto;
import com.zhelandovskiy.t1_task_service.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @HttpLog
    @TimeMetric
    @GetMapping
    public List<TaskDto> getAllTask() {
        return taskService.getAll();
    }

    @GetMapping("/{id}")
    public TaskDto getTaskById(@PathVariable Long id) {
        return taskService.getById(id);
    }

    @HttpLog
    @TimeMetric
    @PostMapping
    public TaskDto create(@RequestBody TaskCreateUpdateDto dto) {
        return taskService.create(dto);
    }

    @PutMapping("/{id}")
    public TaskDto update(@RequestBody TaskCreateUpdateDto dto, @PathVariable Long id) {
        return taskService.update(dto, id);
    }

    @HttpLog
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {
        return taskService.delete(id);
    }
}
