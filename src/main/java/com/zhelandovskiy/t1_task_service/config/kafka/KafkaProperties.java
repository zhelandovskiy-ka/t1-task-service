package com.zhelandovskiy.t1_task_service.config.kafka;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "kafka")
public class KafkaProperties {
    private String groupId;
    private String server;
    private String sessionTimeout;
    private String maxPartitionFetchBytes;
    private String maxPollRecords;
    private String maxPollInterval;
    private String topic;
    private Boolean producerEnable;
}