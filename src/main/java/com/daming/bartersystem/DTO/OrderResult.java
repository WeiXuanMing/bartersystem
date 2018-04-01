package com.daming.bartersystem.DTO;

import com.daming.bartersystem.entitys.BarterOrder;
import com.daming.bartersystem.entitys.BarterOrderItem;
import com.daming.bartersystem.entitys.Item;

import java.util.List;

public class OrderResult {
    private BarterOrder barterOrder;

    private List<BarterOrderItem> selfOrderList;

    private List<BarterOrderItem> ownerOrderList;

    private Item ownerItem;

    public BarterOrder getBarterOrder() {
        return barterOrder;
    }

    public void setBarterOrder(BarterOrder barterOrder) {
        this.barterOrder = barterOrder;
    }

    public List<BarterOrderItem> getSelfOrderList() {
        return selfOrderList;
    }

    public void setSelfOrderList(List<BarterOrderItem> selfOrderList) {
        this.selfOrderList = selfOrderList;
    }

    public List<BarterOrderItem> getOwnerOrderList() {
        return ownerOrderList;
    }

    public void setOwnerOrderList(List<BarterOrderItem> ownerOrderList) {
        this.ownerOrderList = ownerOrderList;
    }

    public Item getOwnerItem() {
        return ownerItem;
    }

    public void setOwnerItem(Item ownerItem) {
        this.ownerItem = ownerItem;
    }

    @Override
    public String toString() {
        return "OrderResult{" +
                "selfOrderList=" + selfOrderList +
                ", ownerOrderList=" + ownerOrderList +
                ", ownerItem=" + ownerItem +
                '}';
    }
}
