package com.example.nbd.model;


import com.example.nbd.model.enums.ClientType;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@ToString(exclude = "activeRents")


@Data
@Document
public class Client {

    @Id
    private String id;
    private String firstName;
    private boolean isActive;
    private String lastName;
    private ClientType clientType;
    private Address address;
    private List<String> activeRents = new ArrayList<>();

}