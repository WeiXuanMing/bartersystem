package com.daming.bartersystem.service;

import com.daming.bartersystem.entitys.UserReview;

import java.util.List;

public interface ReviewService {
    public List<UserReview> QueryByItemId(Integer itemId);

    public Integer addReview(UserReview userReview);

}
