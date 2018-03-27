package com.daming.bartersystem.DTO;

public class ReviewResult {
    private boolean isSucceed;

    public boolean isSucceed() {
        return isSucceed;
    }

    public void setSucceed(boolean succeed) {
        isSucceed = succeed;
    }

    @Override
    public String toString() {
        return "ReviewResult{" +
                "isSucceed=" + isSucceed +
                '}';
    }

    public ReviewResult(boolean isSucceed) {
        this.isSucceed = isSucceed;
    }
}
