package com.example.nbd.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RentDTO {
    private String rentId;
    private String rentalName;
    private LocalDateTime startLocalDateTime;
    private LocalDateTime endLocalDateTime;
    private String clientId;
    private String virtualDeviceId;

    public RentDTO(Rent rent, String rentalName) {
        this.rentId = rent.getRentId();
        this.rentalName = rentalName;
        this.startLocalDateTime = rent.getStartLocalDateTime();
        this.endLocalDateTime = rent.getEndLocalDateTime();
        this.clientId = rent.getClientId();
        this.virtualDeviceId = rent.getVirtualDeviceId();
    }
}
