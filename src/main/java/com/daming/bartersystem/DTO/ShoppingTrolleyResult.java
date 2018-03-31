package com.daming.bartersystem.DTO;

import com.daming.bartersystem.entitys.ShoppingTrolley;
import com.daming.bartersystem.entitys.ShoppingTrolleyItem;
import lombok.Data;

import java.util.List;

@Data
public class ShoppingTrolleyResult {
    private ShoppingTrolley shoppingTrolley;

    private List<ShoppingTrolleyItem> shoppingTrolleyItem;
}
