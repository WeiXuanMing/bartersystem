package com.daming.bartersystem.controller;

import com.daming.bartersystem.DTO.ItemDetailResult;
import com.daming.bartersystem.DTO.Result;
import com.daming.bartersystem.DTO.SearchResult;
import com.daming.bartersystem.entitys.Item;
import com.daming.bartersystem.entitys.UserReview;
import com.daming.bartersystem.service.ItemService;
import com.daming.bartersystem.service.ReviewService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ItemController {
    @Autowired
    private ItemService itemService;

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/search")
    @ResponseBody
    public SearchResult getByCondition(@RequestParam(value = "condition") String condition,@RequestParam(value = "pageNum",required = false) Integer pageNum,@RequestParam(value = "pageSize",required = false) Integer pageSize){
        if (pageNum == null){
            pageNum = 1;
        }
        if (pageSize == null){
            pageSize = 16;
        }
        if(condition.equals("")){
            condition="shenmedoumeiyou";
        }

        SearchResult searchResult = new SearchResult();
        PageHelper.startPage(pageNum, pageSize);
        List<Item> items = itemService.queryList(condition);
        PageInfo<Item> info = new PageInfo<Item>(items);
        searchResult.setItems(items);
        searchResult.setPageInfo(info);
        return searchResult;
    }
    @GetMapping("/itemDetail/{itemId}")
    @ResponseBody
    public Result<ItemDetailResult> getItemDetail(@PathVariable("itemId") Integer itemId){
        Result<ItemDetailResult> result = new Result<ItemDetailResult>(0,"failure",new ItemDetailResult());
        ItemDetailResult itemDetailResult = new ItemDetailResult();
        Item item = itemService.query(itemId);
        System.out.println(item);
        if(item!=null){
            itemDetailResult.setItem(item);
            List<UserReview> userReviews =  reviewService.QueryByItemId(item.getItemId());
            itemDetailResult.setUserReviews(userReviews);
            result.setData(itemDetailResult);
            result.setMessage("succeed");
        }
        return result;
    }
}
