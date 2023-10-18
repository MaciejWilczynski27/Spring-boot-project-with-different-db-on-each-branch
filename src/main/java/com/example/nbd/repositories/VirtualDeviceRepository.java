package com.example.nbd.repositories;

import com.example.nbd.model.VirtualDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface VirtualDeviceRepository extends JpaRepository<VirtualDevice,Long> {
}
