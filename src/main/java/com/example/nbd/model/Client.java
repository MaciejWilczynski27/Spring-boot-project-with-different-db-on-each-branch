package com.example.nbd.model;


import com.example.nbd.model.enums.ClientType;

import lombok.*;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.ArrayList;
import java.util.List;

@Data
@Table
public class Client {

    @PrimaryKey
    private String id;
    private String firstName;
    private boolean isActive;
    private String lastName;
    private ClientType clientType;
    private Address address;
    private List<String> activeRents = new ArrayList<>();

}