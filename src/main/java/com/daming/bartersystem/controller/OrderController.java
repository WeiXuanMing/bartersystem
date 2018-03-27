package com.daming.bartersystem.controller;

import com.daming.bartersystem.DTO.OrderResult;
import com.daming.bartersystem.DTO.Result;
import com.daming.bartersystem.entitys.BarterOrder;
import com.daming.bartersystem.entitys.BarterOrder_OrderItem;
import com.daming.bartersystem.entitys.Item;
import com.daming.bartersystem.service.BarterOrder_OrderItemService;
import com.daming.bartersystem.service.ItemService;
import com.daming.bartersystem.service.OrderItemService;
import com.daming.bartersystem.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private BarterOrder_OrderItemService barterOrder_orderItemService;
    @Autowired
    private ItemService itemService;
    @GetMapping("/getOrder/{itemId}")
    @ResponseBody
    public Result<OrderResult> getOrder(HttpSession session, @PathVariable("itemId") Integer itemId){
        Integer uid = (Integer) session.getAttribute("uid");
        Result<OrderResult> result = new Result<OrderResult>(0,"failure",new OrderResult());
        if (uid != null){
            //已登录
            //获取BarterOrder
            BarterOrder barterOrder = orderService.queryByUidAndItemId(uid,itemId);
            if (barterOrder == null){
                //没有创建过订单
                boolean isCreateSucceed = orderService.CreateOrder(uid,itemId);
                if(isCreateSucceed){
                    barterOrder = orderService.queryByUidAndItemId(uid,itemId);
                    Item item = itemService.query(itemId);
                    OrderResult orderResult = new OrderResult();
                    orderResult.setOwnerItem(item);
                    result = new Result<OrderResult>(0,"订单创建成功",orderResult);
                }else {
                    result = new Result<OrderResult>(0,"订单创建失败",new OrderResult());
                }
            }else {
                //已有订单
                /*
                * 首先由orderId获取BarterOrder_OrderItem表里面的orderID和orderItemId的映射
                * 然后获取orderitem表记录
                * */
                Integer barterOrderId = barterOrder.getBarterOrderId();
                List<BarterOrder_OrderItem> barterOrder_orderItem = barterOrder_orderItemService.queryByOrderId(barterOrderId);

            }
        }
        result = new Result<OrderResult>(0,"isNotLogin",new OrderResult());
        return result;


    }

}
