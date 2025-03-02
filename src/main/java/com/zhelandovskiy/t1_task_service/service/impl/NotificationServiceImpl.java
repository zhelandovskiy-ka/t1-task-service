package com.zhelandovskiy.t1_task_service.service.impl;

import com.zhelandovskiy.t1_task_service.dto.TaskDto;
import com.zhelandovskiy.t1_task_service.service.MailService;
import com.zhelandovskiy.t1_task_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final MailService mailService;
    private final JavaMailSender mailSender;

    @Value("${mail-service.recipient:empty}")
    private String recipient;

    @Override
    public void sendToEmail(TaskDto task) {
        if (!recipient.equals("empty")) {
            String subject = "Task update notification";
            String message = String.format("Статус задачи #%s изменен. Новый статус: '%s'", task.getId(), task.getStatus());

            mailService.send(recipient, subject, message);
        }
    }
}
