package com.example.nbd.model;

import com.example.nbd.model.virtualdevices.VirtualDevice;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
@Data
@Entity
@Table(name = "rents")
public class Rent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rentId;
    private LocalDate beginDate;
    private LocalDate endDate;
    @ManyToOne
    @JoinColumn
    private Client client;
    @ManyToOne
    @JoinColumn
    private VirtualDevice virtualDevice;


}
