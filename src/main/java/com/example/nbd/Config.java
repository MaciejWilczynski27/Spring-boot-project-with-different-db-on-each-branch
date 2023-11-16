package com.example.nbd;

import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
public class Config {
    @Bean
    MongoTransactionManager txManager(MongoDatabaseFactory mongoDbFactory) {
        return new MongoTransactionManager(mongoDbFactory);
    }
    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.data.mongodb1")
    public MongoProperties mongoProperties1() {
        return new MongoProperties();
    }
    @Bean
    @ConfigurationProperties(prefix = "spring.data.mongodb2")
    public MongoProperties mongoProperties2() {
        return new MongoProperties();
    }
    @Bean
    @ConfigurationProperties(prefix = "spring.data.mongodb3")
    public MongoProperties mongoProperties3() {
        return new MongoProperties();
    }

}
