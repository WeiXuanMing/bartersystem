package com.daming.bartersystem.controller;

import com.daming.bartersystem.DTO.*;
import com.daming.bartersystem.entitys.Item;
import com.daming.bartersystem.entitys.UserReview;
import com.daming.bartersystem.service.ItemService;
import com.daming.bartersystem.service.ReviewService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Controller
@SessionAttributes(value = {"uid"})
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
    /*
    *{"code":0,"message":"succeed","data":{"items":[{"itemId":1,"uid":1,"title":"item1_title","shipAddress":"asdasdasd","refPrice":1000.00,"classificationId":1,"stock":1,"ison":1,"description":null},{"itemId":2,"uid":1,"title":"item2_title","shipAddress":"fdsfdsfsdf","refPrice":1000.00,"classificationId":1,"stock":1,"ison":1,"description":null}]}}
    * */
    @GetMapping("/possessions")
    @ResponseBody
    public Result<PossessionsResult> getPossessions(HttpSession session){
        PossessionsResult possessionsResult = new PossessionsResult();
        Result<PossessionsResult> result = new Result<PossessionsResult>(0,"failure",possessionsResult);
        Integer uid = (Integer) session.getAttribute("uid");
        if (uid != null){
            List<Item> items = itemService.queryByUid(uid);
            possessionsResult.setItems(items);
            result =  new Result<PossessionsResult>(0,"succeed",possessionsResult);
        }else if(uid == null){
            result = new Result<PossessionsResult>(0,"isNotLogin",possessionsResult);
        }
        return result;
    }
    @RequestMapping(value = "/ItemUpShelf",method = RequestMethod.POST, consumes = "application/json",produces="application/json")
    @ResponseBody
    public Result ItemUpShelf(@RequestBody String param, HttpSession session) throws IOException {
        Integer uid = (Integer) session.getAttribute("uid");
        Result result = new Result(0,"failure",null);
        if (uid != null ){
            //login
            System.out.println("接受到的json格式是这个样子的："+param);
            System.out.println("已经登录了耶~~(＾－＾)V");
            ObjectMapper objectMapper = new ObjectMapper();
            ItemDataAccepter itemDataAccepter = objectMapper.readValue(param, ItemDataAccepter.class);
            String item_title = itemDataAccepter.getTitle();
            String item_shipAddress = itemDataAccepter.getShipAddress();
            BigDecimal item_refPrice = itemDataAccepter.getRefPrice();
            String description = itemDataAccepter.getDescription();
            System.out.println("接受到的item_title是："+item_title);
            System.out.println("接受到的item_shipAddress是："+item_shipAddress);
            System.out.println("接受到的item_refPrice是："+item_refPrice);
            System.out.println("接受到的description是："+description);
            Item item = new Item();
            item.setTitle(item_title);
            item.setUid(uid);
            item.setShipAddress(item_shipAddress);
            item.setRefPrice(item_refPrice);
            item.setClassificationId(1);
            item.setDescription(description);
            item.setStock(1);
            item.setIson(0);
            boolean isSucceed = itemService.addItem(item);
            System.out.println("item插入结果"+isSucceed);
            if(isSucceed){
                result = new Result(0,"succeed",null);
                return result;
            }
        }else {
            //not login
            result = new Result(0,"isNotLogin",null);
            return  result;
        }
        return result;
    }
}
