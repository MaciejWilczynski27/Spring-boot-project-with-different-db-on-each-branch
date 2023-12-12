package com.example.nbd.model.virtualdevices;

import com.example.nbd.model.enums.DatabaseType;
import lombok.*;

@Data
@ToString(callSuper = true)
public class VirtualDatabaseServer extends VirtualDevice {
    private DatabaseType database;
}
