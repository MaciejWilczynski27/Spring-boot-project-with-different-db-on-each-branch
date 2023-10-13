package com.example.nbd;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Client {
    private String firstName;
    private String lastName;
    private ClientType clientType;
    private Address address;
}
