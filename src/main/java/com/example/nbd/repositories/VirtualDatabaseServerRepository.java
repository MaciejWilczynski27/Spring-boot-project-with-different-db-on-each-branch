package com.example.nbd.repositories;


import com.example.nbd.model.virtualdevices.VirtualDatabaseServer;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface VirtualDatabaseServerRepository extends CassandraRepository<VirtualDatabaseServer,String> {
}
