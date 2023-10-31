package com.example.nbd.model;

import com.example.nbd.model.virtualdevices.VirtualDevice;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "rents")
public class Rent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rentId;
    private LocalDateTime startLocalDateTime;
    private LocalDateTime endLocalDateTime;
    @ManyToOne
    @JoinColumn
    private Client client;
    @ManyToOne
    @JoinColumn
    private VirtualDevice virtualDevice;


}
