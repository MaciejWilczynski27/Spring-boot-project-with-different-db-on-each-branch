package com.example.nbd.repositories;

import com.example.nbd.model.Client;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;


public interface ClientRepository extends CassandraRepository<Client,String> {
    @AllowFiltering
    boolean existsByFirstNameAndLastNameAndAddress_CityAndAddress_StreetAndAddress_HouseNumber(String firstName,String lastName,String city, String street, String houseNumber);

}
