package com.example.consumerApp;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface RentRepository extends MongoRepository<Rent, String> {
}
