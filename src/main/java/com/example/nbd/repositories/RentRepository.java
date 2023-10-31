package com.example.nbd.repositories;

import com.example.nbd.model.Rent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;


public interface RentRepository extends JpaRepository<Rent,Long> {
    ArrayList<Rent> findAllByVirtualDeviceId(Long virtualDeviceId);
}
