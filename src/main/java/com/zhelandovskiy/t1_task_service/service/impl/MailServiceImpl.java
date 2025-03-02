package com.zhelandovskiy.t1_task_service.service.impl;

import com.zhelandovskiy.t1_task_service.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String senderName;

    @Override
    public void send(String to, String subject, String message) {
        log.info("Отправка email на {}, from {}", to, senderName);

        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(senderName);
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