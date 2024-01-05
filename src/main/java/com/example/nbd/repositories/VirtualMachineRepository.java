package com.example.nbd.repositories;

import com.example.nbd.model.virtualdevices.VirtualMachine;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface VirtualMachineRepository extends CassandraRepository<VirtualMachine,String> {
}
