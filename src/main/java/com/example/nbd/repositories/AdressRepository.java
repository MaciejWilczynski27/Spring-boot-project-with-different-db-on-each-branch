package com.example.nbd.repositories;

import com.example.nbd.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdressRepository extends JpaRepository<Address,Long> {
}
