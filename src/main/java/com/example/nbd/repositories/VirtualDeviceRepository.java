package com.example.nbd.repositories;



import com.example.nbd.model.virtualdevices.VirtualDevice;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface VirtualDeviceRepository extends CassandraRepository<VirtualDevice,String> {
}
