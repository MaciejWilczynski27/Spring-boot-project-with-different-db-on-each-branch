package com.example.nbd.model.virtualdevices;

import com.example.nbd.model.enums.DatabaseType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Data
@ToString(callSuper = true)
@Entity
@DiscriminatorValue("virtualdatabaseserver")
public class VirtualDatabaseServer extends VirtualDevice {
    private DatabaseType database;
}
