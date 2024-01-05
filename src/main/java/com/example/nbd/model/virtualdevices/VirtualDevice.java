package com.example.nbd.model.virtualdevices;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Getter
@Setter
@ToString
public abstract class VirtualDevice {
    @PrimaryKey
    private String id;
    private int storageSize;
    private int cpuCores;
    private int ram;
}
