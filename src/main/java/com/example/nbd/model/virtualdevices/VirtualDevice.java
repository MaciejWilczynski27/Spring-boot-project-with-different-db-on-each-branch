package com.example.nbd.model.virtualdevices;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public abstract class VirtualDevice implements Serializable {
    @Id
    private String id;
    private int storageSize;
    private int cpuCores;
    private int ram;
}
