package com.daming.bartersystem.service.impl;

import com.daming.bartersystem.dao.UserReviewMapper;
import com.daming.bartersystem.entitys.UserReview;
import com.daming.bartersystem.entitys.UserReviewExample;
import com.daming.bartersystem.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService{
    @Autowired
    private UserReviewMapper userReviewMapper;

    public List<UserReview> QueryByItemId(Integer itemId) {
        UserReviewExample userReviewExample = new UserReviewExample();
        UserReviewExample.Criteria criteria = userReviewExample.createCriteria();
        criteria.andItemIdEqualTo(itemId);
        List<UserReview> userReviews = userReviewMapper.selectByExampleWithBLOBs(userReviewExample);
        if (userReviews.size() > 0){
            return userReviews;
        }
        return null;
    }

    public Integer addReview(UserReview userReview) {
        if (userReview!=null) {
            userReviewMapper.insert(userReview);
            return 1;
        }
        return 0;
    }

    public Integer QueryReviewNumByItemId(Integer itemId) {
        List<UserReview> userReviews = QueryByItemId(itemId);
        Integer num = userReviews.size();
        if (num > 0){
            return num;
        }
        return 0;
    }
}
