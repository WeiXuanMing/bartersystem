package com.daming.bartersystem.controller;

import com.daming.bartersystem.DTO.Result;
import com.daming.bartersystem.DTO.ReviewResult;
import com.daming.bartersystem.entitys.UserReview;
import com.daming.bartersystem.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/getReviewNum/{itemId}")
    @ResponseBody
    public Result<Integer> getReviewNumByItemId(@PathVariable("itemId") Integer itemId){
        Result<Integer> result = new Result<Integer>(0,"failure",0);
        Integer num = reviewService.QueryReviewNumByItemId(itemId);
        if (num > 0){
            result = new Result<Integer>(0,"succeed",num);
            return result;
        }

        return  result;
    }
    /*
        map内容:
        itemId:
        reviewContent:
     */
    @RequestMapping(value = "/review", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public Result<ReviewResult> review(@RequestBody Map<String, String> map, HttpSession session){
        Result<ReviewResult> result = new Result<ReviewResult>(0,"failure",new ReviewResult(false));
        Integer itemId = Integer.parseInt(map.get("itemId"));
        String reviewContent = map.get("reviewContent");
        Integer uid = (Integer) session.getAttribute("uid");
        if (uid == null){
            result = new Result<ReviewResult>(0,"isNotLogin",new ReviewResult(false));
        }else if(uid != null){
            UserReview userReview = new UserReview();
            userReview.setItemId(itemId);
            userReview.setReviewContent(reviewContent);
            userReview.setReviewFatherId(uid);
            reviewService.addReview(userReview);
            result = new Result<ReviewResult>(0,"succeed",new ReviewResult(true));
        }
        return result;
    }



}
