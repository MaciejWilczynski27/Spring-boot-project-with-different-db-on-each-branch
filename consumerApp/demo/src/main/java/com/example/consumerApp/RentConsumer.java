package com.example.consumerApp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RentConsumer {
    @Value("${kafka.topic1.name}")
    private String topicName;
    private final RentRepository rentRepository;

    @SneakyThrows
    @KafkaListener(topics = "${kafka.topic1.name}", concurrency = "2")
    public void consume(ConsumerRecord<String, String> payload){
        Rent rent = objectMapper().readValue(payload.value(), Rent.class);
        System.out.println("\n" + topicName+ ":  " + rent);
        var rent1 = rentRepository.save(rent);
        System.out.println("Saved: " + rent1);

    }
    @Bean
    private ObjectMapper objectMapper(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

}

