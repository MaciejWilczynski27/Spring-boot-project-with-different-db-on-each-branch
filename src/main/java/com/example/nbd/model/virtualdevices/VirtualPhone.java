package com.example.nbd.model.virtualdevices;

import lombok.*;


@Data
@ToString(callSuper = true)

public class VirtualPhone extends VirtualDevice {
    private int phoneNumber;

}
