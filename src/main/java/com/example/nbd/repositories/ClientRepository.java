package com.example.nbd.repositories;

import com.example.nbd.model.Client;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface ClientRepository extends MongoRepository<Client,String> {
    boolean existsByFirstNameAndLastNameAndAddress_CityAndAddress_StreetAndAddress_HouseNumber(String firstName,String lastName,String city, String street, String houseNumber);

}
