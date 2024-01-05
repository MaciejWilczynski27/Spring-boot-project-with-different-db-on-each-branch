package com.example.nbd.repositories;

import com.example.nbd.model.virtualdevices.VirtualPhone;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface VirtualPhoneRepository extends CassandraRepository<VirtualPhone,String> {
}
