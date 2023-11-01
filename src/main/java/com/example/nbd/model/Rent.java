package com.example.nbd.model;

import com.example.nbd.model.virtualdevices.VirtualDevice;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document
public class Rent {
    @Id
    private String rentId;
    private LocalDateTime startLocalDateTime;
    private LocalDateTime endLocalDateTime;
    private String clientId;
    private String virtualDeviceId;


}
