package com.example.nbd.model.virtualdevices;

import com.example.nbd.model.enums.DatabaseType;
import lombok.*;
import org.springframework.data.cassandra.core.mapping.Table;

@Data
@ToString(callSuper = true)
@Table
public class VirtualDatabaseServer extends VirtualDevice {
    private DatabaseType database;
}
