package com.example.nbd;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Address {
    private String city;
    private String street;
    private String houseNumber;
}
