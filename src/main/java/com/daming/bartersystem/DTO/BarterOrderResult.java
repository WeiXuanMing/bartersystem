package com.daming.bartersystem.DTO;

import com.daming.bartersystem.entitys.BarterOrder;
import com.daming.bartersystem.entitys.BarterOrderItem;

import java.util.List;


public class BarterOrderResult {
    //订单信息
    private BarterOrder barterOrder;
    //子订单信息
    private List<BarterOrderItem> barterOrderItems;

    public BarterOrder getBarterOrder() {
        return barterOrder;
    }

    public void setBarterOrder(BarterOrder barterOrder) {
        this.barterOrder = barterOrder;
    }

    public List<BarterOrderItem> getBarterOrderItems() {
        return barterOrderItems;
    }

    public void setBarterOrderItems(List<BarterOrderItem> barterOrderItems) {
        this.barterOrderItems = barterOrderItems;
    }

    @Override
    public String toString() {
        return "BarterOrderResult{" +
                "barterOrder=" + barterOrder +
                ", barterOrderItems=" + barterOrderItems +
                '}';
    }
}
