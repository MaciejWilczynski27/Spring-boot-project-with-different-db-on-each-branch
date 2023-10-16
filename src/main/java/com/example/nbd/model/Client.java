package com.example.nbd.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Client {
    private String firstName;
    private String lastName;
    private ClientType clientType;
    private Address address;
}
