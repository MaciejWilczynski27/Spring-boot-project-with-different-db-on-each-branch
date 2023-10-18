package com.example.nbd.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class VirtualDevice {

    private String objectType;
    private int storageSize;
    boolean isRented;
    private int cpuCores;
    private int ram;
}
