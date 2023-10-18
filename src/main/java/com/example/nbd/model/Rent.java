package com.example.nbd.model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@Table(name = "rents")
public class Rent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rentId;
    private LocalDate beginDate;
    private LocalDate endDate;
    private Long clientID;
    private Long virtualDeviceID;


}
