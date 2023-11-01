package com.example.nbd.repositories;

import com.example.nbd.model.Rent;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.ArrayList;


public interface RentRepository extends MongoRepository<Rent,String> {
    ArrayList<Rent> findAllByVirtualDeviceId(String virtualDeviceId);
}
