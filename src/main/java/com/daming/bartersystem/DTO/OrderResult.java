package com.daming.bartersystem.DTO;

import com.daming.bartersystem.entitys.BarterOrderItem;
import com.daming.bartersystem.entitys.Item;

import java.util.List;

public class OrderResult {

    private List<BarterOrderItem> selfOrderListResult;

    private List<BarterOrderItem> ownerOrderListResult;

    private Item ownerItem;

    public Item getOwnerItem() {
        return ownerItem;
    }

    public void setOwnerItem(Item ownerItem) {
        this.ownerItem = ownerItem;
    }

    public List<BarterOrderItem> getSelfOrderListResult() {
        return selfOrderListResult;
    }

    public void setSelfOrderListResult(List<BarterOrderItem> selfOrderListResult) {
        this.selfOrderListResult = selfOrderListResult;
    }

    public List<BarterOrderItem> getOwnerOrderListResult() {
        return ownerOrderListResult;
    }

    public void setOwnerOrderListResult(List<BarterOrderItem> ownerOrderListResult) {
        this.ownerOrderListResult = ownerOrderListResult;
    }

    @Override
    public String toString() {
        return "OrderResult{" +
                "selfOrderListResult=" + selfOrderListResult +
                ", ownerOrderListResult=" + ownerOrderListResult +
                ", ownerItem=" + ownerItem +
                '}';
    }
}
