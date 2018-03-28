package com.daming.bartersystem.DTO;

public class SubmitOrderItemResult {
    private boolean succeed;

    public boolean isSucceed() {
        return succeed;
    }

    public void setSucceed(boolean succeed) {
        this.succeed = succeed;
    }

    @Override
    public String toString() {
        return "SubmitOrderItemResult{" +
                "succeed=" + succeed +
                '}';
    }
}
