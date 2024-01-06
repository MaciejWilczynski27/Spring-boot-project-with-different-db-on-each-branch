package com.example.nbd.repositories;

import com.example.nbd.model.Rent;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.ArrayList;


public interface RentRepository extends CassandraRepository<Rent,String> {
    @AllowFiltering
    ArrayList<Rent> findAllByVirtualDeviceId(String virtualDeviceId);
}
