package com.daming.bartersystem.service;

import com.daming.bartersystem.entitys.UserReview;

import java.util.List;

public interface ReviewService {
     List<UserReview> QueryByItemId(Integer itemId);

     Integer addReview(UserReview userReview);

     Integer QueryReviewNumByItemId(Integer itemId);
}
