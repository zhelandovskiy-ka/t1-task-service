package com.zhelandovskiy.t1_task_service.service.impl;

import com.zhelandovskiy.t1_task_service.config.mail.MailProperties;
import com.zhelandovskiy.t1_task_service.dto.TaskDto;
import com.zhelandovskiy.t1_task_service.service.MailService;
import com.zhelandovskiy.t1_task_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@EnableConfigurationProperties(MailProperties.class)
public class NotificationServiceImpl implements NotificationService {
    private final MailService mailService;
    private final MailProperties mailProperties;

    @Override
    public void sendToEmail(TaskDto task) {
        String subject = "Task update notification";
        String message = String.format("Статус задачи #%s изменен. Новый статус: '%s'", task.getId(), task.getStatus());

        mailService.send(mailProperties.getRecipient(), subject, message);
    }
}
