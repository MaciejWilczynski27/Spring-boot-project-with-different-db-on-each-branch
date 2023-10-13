package com.example.nbd;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class VirtualMachine {
    private int cpuCores;
    private int ram;
    private OperatingSystem operatingSystem;
    private int storageSize;
    boolean isRented;
}
