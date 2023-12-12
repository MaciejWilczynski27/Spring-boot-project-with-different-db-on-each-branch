package com.example.nbd.model;

import com.example.nbd.model.virtualdevices.VirtualDevice;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Table
public class Rent {
    @PrimaryKey
    private String rentId;
    private LocalDateTime startLocalDateTime;
    private LocalDateTime endLocalDateTime;
    private String clientId;
    private String virtualDeviceId;


}
