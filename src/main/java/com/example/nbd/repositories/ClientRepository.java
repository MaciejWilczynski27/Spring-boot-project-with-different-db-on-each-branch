package com.example.nbd.repositories;

import com.example.nbd.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;



public interface ClientRepository extends JpaRepository<Client,Long>{
    boolean existsByFirstNameAndLastNameAndAddress_CityAndAddress_StreetAndAddress_HouseNumber(String firstName,String lastName,String city, String street, String houseNumber);

}
