package com.example.nbd.model;


import jakarta.persistence.*;
import lombok.*;





@Entity
@Table(name = "clients")
@Data
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
   // private boolean isActive;
    private String lastName;
    private ClientType clientType;
    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;
}