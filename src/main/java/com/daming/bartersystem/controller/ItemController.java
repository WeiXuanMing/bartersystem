package com.daming.bartersystem.controller;

import com.daming.bartersystem.DTO.SearchResult;
import com.daming.bartersystem.entitys.Item;
import com.daming.bartersystem.service.ItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ItemController {
    @Autowired
    private ItemService itemService;

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
}
