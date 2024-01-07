package com.example.nbd;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfiguration {

    @Value("${kafka.topic1.name}")
    private String topic1Name;

    @Value("${kafka.topic1.partition-count}")
    private Integer topic1PartitionCount;

    @Value("${kafka.topic1.replica-count}")
    private Integer topic1ReplicaCount;
    @Bean
    public NewTopic compactTopicExample() {
        return TopicBuilder.name(topic1Name)
                .partitions(topic1PartitionCount)
                .replicas(topic1ReplicaCount)
                .compact()
                .build();
    }
}
