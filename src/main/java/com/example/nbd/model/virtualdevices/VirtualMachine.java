package com.example.nbd.model.virtualdevices;

import com.example.nbd.model.enums.OperatingSystemType;
import lombok.*;
import org.springframework.data.cassandra.core.mapping.Table;


@Data
@ToString(callSuper = true)
@Table
public class VirtualMachine extends VirtualDevice {
    private OperatingSystemType operatingSystemType;
}
