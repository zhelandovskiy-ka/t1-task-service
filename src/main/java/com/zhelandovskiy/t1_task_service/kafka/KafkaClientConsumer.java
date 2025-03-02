package com.zhelandovskiy.t1_task_service.kafka;

import com.zhelandovskiy.t1_task_service.dto.TaskDto;
import com.zhelandovskiy.t1_task_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaClientConsumer {
    private final NotificationService notificationService;

    @KafkaListener(id = "${kafka.group-id}", topics = "${kafka.topic}", containerFactory = "kafkaListenerContainerFactory")
    public void listener(@Payload TaskDto task,
                         Acknowledgment ack,
                         @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                         @Header(KafkaHeaders.RECEIVED_KEY) String key) {
        log.info("Client consumer: обработка новых сообщений. From topic: {}", topic);

        try {
            notificationService.sendToEmail(task);
        } finally {
            ack.acknowledge();
        }

        log.info("Client consumer: обработка завершена");
    }
}