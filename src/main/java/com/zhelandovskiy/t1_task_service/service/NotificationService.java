package com.zhelandovskiy.t1_task_service.service;

import com.zhelandovskiy.t1_task_service.dto.TaskDto;

public interface NotificationService {

    void sendToEmail(TaskDto taskDto);

}
