package com.example.nbd.model;


import com.example.nbd.model.enums.ClientType;

import lombok.*;
import org.springframework.data.cassandra.core.mapping.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@Table
public class Client {

    @PrimaryKey
    private String id;
    private String firstName;
    private boolean isActive;
    private String lastName;
    private ClientType clientType;
    @Embedded.Nullable
    private Address address;
    @Frozen
    private List<String> activeRents = new ArrayList<>();

}
