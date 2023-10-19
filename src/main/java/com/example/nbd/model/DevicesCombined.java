package com.example.nbd.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class DevicesCombined extends VirtualDevice{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Embedded
    private VirtualDatabaseServer virtualDatabaseServer;

    @Embedded
    private VirtualMachine virtualMachine;

    @Embedded
    private VirtualPhone virtualPhone;

    private DeviceType deviceType;

}
