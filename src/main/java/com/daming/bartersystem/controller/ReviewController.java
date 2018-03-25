package com.daming.bartersystem.controller;

import com.daming.bartersystem.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ReviewController {

    @Autowired
    private ReviewService reviewService;


}
