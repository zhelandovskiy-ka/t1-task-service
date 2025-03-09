package com.zhelandovskiy.t1_task_service.config.mail;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "mail-service")
public class MailProperties {
    private String sender;
    private String recipient;
}