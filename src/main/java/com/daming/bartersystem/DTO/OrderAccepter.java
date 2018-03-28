package com.daming.bartersystem.DTO;

import org.hibernate.validator.internal.constraintvalidators.bv.past.PastValidatorForReadableInstant;

import java.util.List;

public class OrderAccepter {
    private Integer orderId;

    private List<Integer> selfOrderItemIdList;

    private String receiver_address;

    public String getReceiver_address() {
        return receiver_address;
    }

    public void setReceiver_address(String receiver_address) {
        this.receiver_address = receiver_address;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public List<Integer> getSelfOrderItemIdList() {
        return selfOrderItemIdList;
    }

    public void setSelfOrderItemIdList(List<Integer> selfOrderItemIdList) {
        this.selfOrderItemIdList = selfOrderItemIdList;
    }

    @Override
    public String toString() {
        return "OrderAccepter{" +
                "orderId=" + orderId +
                ", selfOrderItemIdList=" + selfOrderItemIdList +
                ", receive_address='" + receiver_address + '\'' +
                '}';
    }
}
