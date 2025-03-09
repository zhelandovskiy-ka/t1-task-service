package com.zhelandovskiy.t1_task_service.dto;

import lombok.Data;

@Data
public class TaskCreateUpdateDto {
    private String tittle;
    private String description;
    private String status;
    private Long userId;
}
