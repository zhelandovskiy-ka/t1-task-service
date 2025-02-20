package com.zhelandovskiy.t1_aop_1.controller;

import com.zhelandovskiy.t1_aop_1.dto.TaskCreateUpdateDto;
import com.zhelandovskiy.t1_aop_1.dto.TaskDto;
import com.zhelandovskiy.t1_aop_1.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping
    public List<TaskDto> getAllTask() {
        return taskService.getAll();
    }

    @GetMapping("/{id}")
    public TaskDto getTaskById(@PathVariable Long id) {
        return taskService.getById(id);
    }

    @PostMapping
    public TaskDto create(@RequestBody TaskCreateUpdateDto dto) {
        return taskService.create(dto);
    }

    @PutMapping("/{id}")
    public TaskDto update(@RequestBody TaskCreateUpdateDto dto, @PathVariable Long id) {
        return taskService.update(dto, id);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {
        return taskService.delete(id);
    }
}
