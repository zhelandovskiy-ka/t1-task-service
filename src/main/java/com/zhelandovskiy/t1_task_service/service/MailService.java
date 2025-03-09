package com.zhelandovskiy.t1_task_service.service;

public interface MailService {

    void send(String to, String subject, String message);

}