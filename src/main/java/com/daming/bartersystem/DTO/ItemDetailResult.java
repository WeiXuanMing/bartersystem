package com.daming.bartersystem.DTO;

import com.daming.bartersystem.entitys.Item;
import com.daming.bartersystem.entitys.UserReview;

import java.util.List;

public class ItemDetailResult {
    private Item item;

    private List<UserReview> userReviews;

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public List<UserReview> getUserReviews() {
        return userReviews;
    }

    public void setUserReviews(List<UserReview> userReviews) {
        this.userReviews = userReviews;
    }

    @Override
    public String toString() {
        return "ItemDetailResult{" +
                "item=" + item +
                ", userReviews=" + userReviews +
                '}';
    }
}
