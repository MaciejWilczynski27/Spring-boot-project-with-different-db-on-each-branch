package com.example.nbd.repositories;

import com.example.nbd.model.Rent;
import org.springframework.data.jpa.repository.JpaRepository;


//rent id po prawo dwupolowy, composite primary key
public interface RentRepository extends JpaRepository<Rent,Long> {
}
