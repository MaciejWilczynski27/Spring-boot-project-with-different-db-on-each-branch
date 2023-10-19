package com.example.nbd.repositories;



import com.example.nbd.model.virtualdevices.VirtualDevice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VirtualDeviceRepository extends JpaRepository<VirtualDevice,Long> {
}
