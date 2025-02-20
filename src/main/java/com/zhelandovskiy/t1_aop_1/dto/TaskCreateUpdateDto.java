package com.zhelandovskiy.t1_aop_1.dto;

import lombok.Data;

@Data
public class TaskCreateUpdateDto {
    private String tittle;
    private String description;
    private Long userId;
}
