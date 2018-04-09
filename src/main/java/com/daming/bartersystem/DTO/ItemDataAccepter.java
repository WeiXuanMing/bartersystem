package com.daming.bartersystem.DTO;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ItemDataAccepter {
    private String title;

    private String shipAddress;

    private BigDecimal refPrice;

    private String description;
}
