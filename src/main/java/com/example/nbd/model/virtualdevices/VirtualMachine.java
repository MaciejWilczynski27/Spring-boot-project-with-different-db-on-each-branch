package com.example.nbd.model.virtualdevices;

import com.example.nbd.model.enums.OperatingSystemType;
import lombok.*;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@ToString(callSuper = true)
@Document
@EqualsAndHashCode(callSuper = true)
public class VirtualMachine extends VirtualDevice {
    private OperatingSystemType operatingSystemType;
}
