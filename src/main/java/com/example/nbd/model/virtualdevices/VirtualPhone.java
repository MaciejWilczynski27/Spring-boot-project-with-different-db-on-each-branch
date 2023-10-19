package com.example.nbd.model.virtualdevices;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Data
@ToString(callSuper = true)
@Entity
@DiscriminatorValue("virtualphone")
public class VirtualPhone extends VirtualDevice {
    private int phoneNumber;

}
