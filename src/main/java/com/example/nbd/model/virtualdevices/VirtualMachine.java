package com.example.nbd.model.virtualdevices;

import com.example.nbd.model.enums.OperatingSystemType;
import lombok.*;


@Data
@ToString(callSuper = true)
public class VirtualMachine extends VirtualDevice {
    private OperatingSystemType operatingSystemType;
}
