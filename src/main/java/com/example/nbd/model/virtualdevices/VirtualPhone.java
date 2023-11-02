package com.example.nbd.model.virtualdevices;

import lombok.*;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@ToString(callSuper = true)
@Document
public class VirtualPhone extends VirtualDevice {
    private int phoneNumber;

}
