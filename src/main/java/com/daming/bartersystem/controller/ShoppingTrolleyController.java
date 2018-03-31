package com.daming.bartersystem.controller;

import com.daming.bartersystem.DTO.Result;
import com.daming.bartersystem.DTO.ShoppingTrolleyResult;
import com.daming.bartersystem.entitys.Item;
import com.daming.bartersystem.entitys.ShoppingTrolley;
import com.daming.bartersystem.entitys.ShoppingTrolleyItem;
import com.daming.bartersystem.service.ItemService;
import com.daming.bartersystem.service.ShoppingTrolleyItemService;
import com.daming.bartersystem.service.ShoppingTrolleyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ShoppingTrolleyController {
    @Autowired
    private ShoppingTrolleyService shoppingTrolleyService;
    @Autowired
    private ShoppingTrolleyItemService shoppingTrolleyItemService;
    @Autowired
    private ItemService itemService;

    @GetMapping("/getShoppingTrolley")
    @ResponseBody
    public Result<ShoppingTrolleyResult> getShoppingTrolley(HttpSession session){
        ShoppingTrolleyResult shoppingTrolleyResult = new ShoppingTrolleyResult();
        Result<ShoppingTrolleyResult> result = new Result<ShoppingTrolleyResult>(0,"failure",shoppingTrolleyResult);
        Integer uid = (Integer) session.getAttribute("uid");
        if (uid != null){
            //login
            //获取用户购物车记录
            ShoppingTrolley shoppingTrolley = shoppingTrolleyService.queryByUid(uid);
            //如果不存在购物车记录则创建一个记录
            if (shoppingTrolley != null){
                //通过购物车id获取购物车详细
                List<ShoppingTrolleyItem> shoppingTrolleyItems = shoppingTrolleyItemService.queryByTrolleyId(shoppingTrolley.getTrolleyId());
                shoppingTrolleyResult.setShoppingTrolleyItem(shoppingTrolleyItems);
                shoppingTrolleyResult.setShoppingTrolley(shoppingTrolley);
                return result = new Result<ShoppingTrolleyResult>(0,"succeed",shoppingTrolleyResult);
            }else {
                shoppingTrolleyService.createShoppingTrolley(uid);
                return result = new Result<ShoppingTrolleyResult>(0,"succeed",shoppingTrolleyResult);
            }
        }else {
            //not login
            return result = new Result<ShoppingTrolleyResult>(0,"isNotLogin",shoppingTrolleyResult);
        }
    }


    @RequestMapping(value = "/addToShoppingTrolley/{itemId}",method = RequestMethod.PUT)
    @ResponseBody
    public Result addToShoppingTrolley(@PathVariable("itemId") Integer itemId,HttpSession session){
        Result result = new Result(0,"failure",null);
        Integer uid = (Integer) session.getAttribute("uid");
        if (uid != null){
            //login
            //获取用户购物车记录
            ShoppingTrolley shoppingTrolley = shoppingTrolleyService.queryByUid(uid);
            //如果不存在购物车记录则创建一个记录
            if (shoppingTrolley != null){
                //添加购物车详情记录
                Item item = itemService.query(itemId);
                if (item != null){
                    //检测是否是自己的物品
                    if (item.getUid() != uid){
                        //检测是否已经存在记录了
                        List<ShoppingTrolleyItem> shoppingTrolleyItems = shoppingTrolleyItemService.queryByTrolleyId(shoppingTrolley.getTrolleyId());
                        System.out.println(shoppingTrolleyItems);
                        if (shoppingTrolleyItems!=null && shoppingTrolleyItems.size()>0) {
                            boolean isExists = false;
                            for (ShoppingTrolleyItem shoppingTrolleyItem : shoppingTrolleyItems) {
                                isExists = shoppingTrolleyItem.getItemId().equals(itemId);
                            }
                            if (!isExists) {
                                //不存在
                                shoppingTrolleyItemService.createShoppingTrolleyItem(shoppingTrolley.getTrolleyId(), itemId);
                                result = new Result(0, "succeed", null);
                                return result;
                            } else {
                                //存在
                                result = new Result(0, "isExists", null);
                                return result;
                            }
                        }else {
                            shoppingTrolleyItemService.createShoppingTrolleyItem(shoppingTrolley.getTrolleyId(), itemId);
                            result = new Result(0, "succeed", null);
                            return result;
                        }
                    }else {
                        //是自己的
                        result = new Result(0,"isYouItem",null);
                        return result;
                    }
                }else {
                    //不存在这个物品
                    result = new Result(0,"NotItem",null);
                    return result;
                }
            }else {
                shoppingTrolleyService.createShoppingTrolley(uid);
                ShoppingTrolley shoppingTrolley1 = shoppingTrolleyService.queryByUid(uid);
                shoppingTrolleyItemService.createShoppingTrolleyItem(shoppingTrolley1.getTrolleyId(),itemId);
                result = new Result(0,"succeed",null);
                return result;
            }
        }else {
            //not login
            return result = new Result(0,"isNotLogin",null);
        }
    }
}
