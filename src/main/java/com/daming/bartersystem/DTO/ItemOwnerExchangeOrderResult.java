package com.daming.bartersystem.DTO;

import com.daming.bartersystem.entitys.BarterOrderItem;

import java.util.List;

public class ItemOwnerExchangeOrderResult {
    private List<BarterOrderItem> selfOrderItemList;

    private List<BarterOrderItem> applicantOrderItemList;

    public List<BarterOrderItem> getSelfOrderItemList() {
        return selfOrderItemList;
    }

    public void setSelfOrderItemList(List<BarterOrderItem> selfOrderItemList) {
        this.selfOrderItemList = selfOrderItemList;
    }

    public List<BarterOrderItem> getApplicantOrderItemList() {
        return applicantOrderItemList;
    }

    public void setApplicantOrderItemList(List<BarterOrderItem> applicantOrderItemList) {
        this.applicantOrderItemList = applicantOrderItemList;
    }

    @Override
    public String toString() {
        return "ItemOwnerExchangeOrderResult{" +
                "selfOrderItemList=" + selfOrderItemList +
                ", applicantOrderItemList=" + applicantOrderItemList +
                '}';
    }
}
