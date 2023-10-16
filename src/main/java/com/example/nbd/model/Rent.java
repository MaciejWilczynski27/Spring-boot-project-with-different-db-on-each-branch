package com.example.nbd.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Rent {
    private Date begin;
    private Date end;
    private UUID clientID;
    private UUID virtualDeviceID;

}
