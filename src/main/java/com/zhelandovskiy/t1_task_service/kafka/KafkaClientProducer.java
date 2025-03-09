package com.zhelandovskiy.t1_task_service.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaClientProducer {
    private final KafkaTemplate kafkaTemplate;

    public void sendTo(String topic, Object o) {
        try {
            log.info("send message to topic: {}, object: {}", topic, o);
            kafkaTemplate.send(topic, o);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}