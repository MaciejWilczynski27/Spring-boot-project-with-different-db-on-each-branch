package com.example.nbd.model.virtualdevices;

import com.example.nbd.model.enums.OperatingSystemType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Data
@ToString(callSuper = true)
@Entity
@DiscriminatorValue("virtualmachine")
public class VirtualMachine extends VirtualDevice {
    private OperatingSystemType operatingSystemType;
}
