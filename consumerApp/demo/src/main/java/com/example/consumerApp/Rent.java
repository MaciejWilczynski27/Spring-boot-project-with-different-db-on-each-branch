package com.example.consumerApp;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document
public class Rent {
    @Id
    private String rentId;
    private String rentalName;
    private LocalDateTime startLocalDateTime;
    private LocalDateTime endLocalDateTime;
    private String clientId;
    private String virtualDeviceId;


}
