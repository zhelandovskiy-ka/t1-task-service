package com.zhelandovskiy.t1_task_service.service.impl;

import com.zhelandovskiy.t1_task_service.config.mail.MailProperties;
import com.zhelandovskiy.t1_task_service.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
    private final JavaMailSender mailSender;
    private final MailProperties mailProperties;

    @Override
    public void send(String to, String subject, String message) {
        log.info("Отправка email на {}, from {}", to, mailProperties.getSender());

        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(mailProperties.getSender());
            simpleMailMessage.setTo(to);
            simpleMailMessage.setSubject(subject);
            simpleMailMessage.setText(message);

            mailSender.send(simpleMailMessage);
        } catch (MailException e) {
            log.error("Ошибка отправки email");
            throw new RuntimeException(e.getMessage());
        }

        log.info("Сообщение отправлено на {}", to);
    }
}