package com.example.nbd.model.virtualdevices;

import com.example.nbd.model.enums.OperatingSystemType;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@ToString(callSuper = true)
@Document
public class VirtualMachine extends VirtualDevice {
    private OperatingSystemType operatingSystemType;
}
