package com.example.nbd.model.virtualdevices;

import lombok.*;
import org.springframework.data.cassandra.core.mapping.Table;


@Data
@ToString(callSuper = true)
@Table
public class VirtualPhone extends VirtualDevice {
    private int phoneNumber;

}
