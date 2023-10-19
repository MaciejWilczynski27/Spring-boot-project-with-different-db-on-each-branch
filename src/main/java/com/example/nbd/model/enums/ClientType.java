package com.example.nbd.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ClientType {
   BRONZE(1),
   SILVER(2),
   GOLD(3),
   DIAMOND(4);
   private final int value;
}
