package com.example.nbd.repositories;



import com.example.nbd.model.virtualdevices.VirtualDevice;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VirtualDeviceRepository extends MongoRepository<VirtualDevice,String> {
}
