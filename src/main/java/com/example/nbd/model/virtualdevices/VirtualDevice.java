package com.example.nbd.model.virtualdevices;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@DiscriminatorColumn(name = "deviceType")
public abstract class VirtualDevice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int storageSize;
    boolean isRented;
    private int cpuCores;
    private int ram;
}
