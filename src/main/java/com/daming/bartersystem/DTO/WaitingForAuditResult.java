package com.daming.bartersystem.DTO;

import com.daming.bartersystem.entitys.Item;
import lombok.Data;

import java.util.List;

@Data
public class WaitingForAuditResult {
    private List<Item> items;
}
