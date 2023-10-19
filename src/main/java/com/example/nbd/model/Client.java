package com.example.nbd.model;


import com.example.nbd.model.enums.ClientType;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@ToString(exclude = "activeRents")

@Entity
@Table(name = "clients")
@Data
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private boolean isActive;
    private String lastName;
    private ClientType clientType;
    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;
    @OneToMany(fetch = FetchType.EAGER,mappedBy = "client",cascade = CascadeType.ALL)
    private List<Rent> activeRents = new ArrayList<>();
}