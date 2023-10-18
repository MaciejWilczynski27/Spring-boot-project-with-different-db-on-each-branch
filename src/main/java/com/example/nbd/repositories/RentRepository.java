package com.example.nbd.repositories;

import com.example.nbd.model.Rent;
import org.springframework.data.jpa.repository.JpaRepository;



public interface RentRepository extends JpaRepository<Rent,Long> {
}
