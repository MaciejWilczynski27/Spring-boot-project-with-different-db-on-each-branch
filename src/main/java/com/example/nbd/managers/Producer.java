package com.example.nbd.managers;

import com.example.nbd.model.Rent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Producer {
    private final KafkaTemplate<String, Rent> kafkaTemplate;

    @Value("${kafka.topic1.name}")
    private String topicName;

    @Value("${kafka.topic1.key1}")
    private String keyName;
    public void sendRent(Rent rent) {
        kafkaTemplate.send(topicName,keyName, rent);
    }
}
