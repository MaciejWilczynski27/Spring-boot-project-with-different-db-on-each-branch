package com.example.nbd.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class VirtualMachine {
    private int cpuCores;
    private int ram;
    private OperatingSystem operatingSystem;
    private int storageSize;
    boolean isRented;
}
