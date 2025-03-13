package com.zhelandovskiy.t1_task_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {
    private Long id;
    private String tittle;
    private String description;
    private String status;
    private Long userId;
}


