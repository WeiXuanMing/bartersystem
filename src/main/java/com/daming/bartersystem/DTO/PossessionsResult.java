package com.daming.bartersystem.DTO;

import com.daming.bartersystem.entitys.Item;

import java.util.List;

public class PossessionsResult {
    private List<Item> items;

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "PossessionsResult{" +
                "items=" + items +
                '}';
    }
}
