package com.example.nbd.managers;

import com.example.nbd.model.Rent;
import com.example.nbd.model.RentDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class Producer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Value("${kafka.topic1.name}")
    private String topicName;

    @Value("${kafka.topic1.key1}")
    private String keyName;
    @SneakyThrows
    public void sendRent(RentDTO rent) {
        kafkaTemplate.send(topicName, UUID.randomUUID().toString(), objectMapper.writeValueAsString(rent));
    }
}
