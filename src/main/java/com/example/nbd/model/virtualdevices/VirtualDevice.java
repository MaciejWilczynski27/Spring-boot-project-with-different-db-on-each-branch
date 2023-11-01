package com.example.nbd.model.virtualdevices;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString
public abstract class VirtualDevice {
    @Id
    private String id;
    private int storageSize;
    private int cpuCores;
    private int ram;
}
