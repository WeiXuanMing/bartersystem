package com.daming.bartersystem.controller;

import com.daming.bartersystem.DTO.OrderAccepter;
import com.daming.bartersystem.DTO.OrderResult;
import com.daming.bartersystem.DTO.Result;
import com.daming.bartersystem.entitys.BarterOrder;
import com.daming.bartersystem.entitys.BarterOrderItem;
import com.daming.bartersystem.entitys.BarterOrder_OrderItem;
import com.daming.bartersystem.entitys.Item;
import com.daming.bartersystem.service.BarterOrder_OrderItemService;
import com.daming.bartersystem.service.ItemService;
import com.daming.bartersystem.service.OrderItemService;
import com.daming.bartersystem.service.OrderService;
import example.OrderItemExample;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
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

    /**
     * 返回的message有：
     * failure：未知错误
     * NotItem：没有这个物品
     * isYouItem：向自己的物品提出申请
     * 订单创建成功：表示第一次想物品提出申请，订单未创建
     * 订单创建失败：未知错误导致订单创建失败
     * succeed：返回自己的物品和他人的物品列表
     */

    @GetMapping("/getOrder/{itemId}")
    @ResponseBody
    public Result<OrderResult> getOrder(HttpSession session, @PathVariable("itemId") Integer itemId) {
        Integer uid = (Integer) session.getAttribute("uid");
        Result<OrderResult> result = new Result<OrderResult>(0, "failure", new OrderResult());


        if (uid != null){
            Item item = itemService.query(itemId);
            if (item == null){
                result = new Result<OrderResult>(0, "NotItem", new OrderResult());
                return result;
            }else {
                //物品存在
                if(uid == item.getUid()){
                    result = new Result<OrderResult>(0, "isYouItem", new OrderResult());
                    return result;
                }
            }
            //已登录
            //获取BarterOrder
            BarterOrder barterOrder = orderService.queryByUidAndItemId(uid,itemId);
            if (barterOrder == null){
                //没有创建过订单
                boolean isCreateSucceed = orderService.CreateOrder(uid,itemId);
                if(isCreateSucceed){
                    System.out.println("订单未创建，正在创建~");
                    //创建订单这里未完成，应该同样返回一个result，默认的将发出申请的起始物品加入到orderitem表里面去，将状态设置为未确认
                    barterOrder = orderService.queryByUidAndItemId(uid,itemId);
                    Integer orderId = barterOrder.getBarterOrderId(); //订单编号
                    BarterOrderItem barterOrderItem = new BarterOrderItem();
                    barterOrderItem.setOrderId(barterOrder.getBarterOrderId());
                    barterOrderItem.setItemId(barterOrder.getItemId());
                    Integer uid1 = itemService.query(itemId).getUid();
                    barterOrderItem.setUid1(uid1);
                    barterOrderItem.setUid2(uid);
                    barterOrderItem.setOrderItemState(0);
                    orderItemService.insert(barterOrderItem);
                    BarterOrderItem originalBarterOrderItem = orderItemService.queryByUidUidItemId(uid1, uid, itemId);//将起始物品的记录取出来
                    Integer originalBarterOrderItemId = originalBarterOrderItem.getOrderitemId();
                    //创建barterOrder_orderItem联系
                    BarterOrder_OrderItem barterOrder_orderItem = new BarterOrder_OrderItem();
                    barterOrder_orderItem.setBarterOrderId(orderId);
                    barterOrder_orderItem.setOrderitemId(originalBarterOrderItemId);
                    barterOrder_orderItem.setAbandoned(0);
                    System.out.println("正在创建订单表和订单详细表的联系，"+barterOrder_orderItem);
                    barterOrder_orderItemService.addBarterOrder_OrderItem(barterOrder_orderItem);
                    System.out.println("订单创建成功，详细信息"+originalBarterOrderItem);
                    OrderResult orderResult = new OrderResult();
                    List<BarterOrderItem> ownerItemList = new ArrayList<BarterOrderItem>();
                    ownerItemList.add(originalBarterOrderItem);
                    orderResult.setOwnerOrderList(ownerItemList);
                    result = new Result<OrderResult>(0,"订单创建成功",orderResult);
                    return result;
                }else {
                    result = new Result<OrderResult>(0,"订单创建失败",new OrderResult());
                    return result;
                }
            }else {
                //已有订单
                /*
                * 首先由orderId获取BarterOrder_OrderItem表里面的orderID和orderItemId的映射
                * 然后获取orderitem表记录
                * */
                System.out.println("已有订单");
                Integer barterOrderId = barterOrder.getBarterOrderId();
                List<BarterOrder_OrderItem> barterOrder_orderItems = barterOrder_orderItemService.queryByOrderId(barterOrderId);
                System.out.println("从BarterOrder_OrderItem表中取出了"+barterOrder_orderItems+"条记录");
                List<BarterOrderItem> selfOrderList = new ArrayList<BarterOrderItem>();
                List<BarterOrderItem> ownerOrderList = new ArrayList<BarterOrderItem>();
                if(barterOrder_orderItems!=null&&barterOrder_orderItems.size()>0){
                    for (int i = 0;i < barterOrder_orderItems.size();i++) {
                        System.out.println("获取到的映射："+barterOrder_orderItems.get(i));
                        BarterOrderItem barterOrderItem =  orderItemService.queryByOrderItemId(barterOrder_orderItems.get(i).getOrderitemId());
                        System.out.println("获取到的订单详细："+barterOrderItem);
                        System.out.println("第一次取得uid是"+barterOrderItem.getUid1());
                        if (barterOrderItem.getUid1() == uid){
                            System.out.println("将取得的uid"+barterOrderItem.getUid1()+"放进selfOrderList中");
                            selfOrderList.add(barterOrderItem);
                        }else{
                            System.out.println("将取得的uid"+barterOrderItem.getUid1()+"放进ownerOrderList");
                            ownerOrderList.add(barterOrderItem);
                        }
                    }
                }
                OrderResult orderResult = new OrderResult();
                orderResult.setSelfOrderList(selfOrderList);
                orderResult.setOwnerOrderList(ownerOrderList);
                result = new Result<OrderResult>(0,"succeed",orderResult);
                return result;
            }
        }
        result = new Result<OrderResult>(0,"isNotLogin",new OrderResult());
        return result;


    }

    /**
     * 提交订单详细信息，信息包含自己提供的物品信息
     * @param
     * @param orderId
     * @return
     */

    @RequestMapping(value = "/submitOrderItem/{orderId}", method = RequestMethod.POST, produces="application/json")
    @ResponseBody
    public Integer submitOrderItem(@RequestBody String param,@PathVariable("orderId") Integer orderId,HttpSession session) throws IOException {
        System.out.println("接受到的json格式是这个样子的："+param);
        param = new String(param.getBytes("ISO-8859-1"), "UTF-8");
        Integer uid = (Integer) session.getAttribute("uid");

        /*
        中文乱码问题还可以通过修改tomcat配置文件/conf/server.xml 加上
            <Connector port="8080" protocol="HTTP/1.1"

                   connectionTimeout="20000"

                   redirectPort="8443" URIEncoding="UTF-8" />
                   解决
         */
        if(uid != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            OrderAccepter orderAccepter = objectMapper.readValue(param, OrderAccepter.class);
            System.out.println("获取到的SelfOrderItemIdList是：" + orderAccepter.getSelfOrderItemIdList());
            System.out.println("获取到的Receiver_address是：" + orderAccepter.getReceiver_address());
            System.out.println("获取到的OrderId是" + orderAccepter.getOrderId());
            //获取属性
            List<Integer> selfOrderItemIdList = orderAccepter.getSelfOrderItemIdList();
            String receiver_address = orderAccepter.getReceiver_address();
            Integer accptedOrderId = orderAccepter.getOrderId();
            //然后加入orderItem表，在加入barterorder_orderitem的关系映射表,将状态设置成未确认状态
            BarterOrder barterOrder = orderService.queryByOrderId(orderId);

            Integer otherId;
            for (int i = 0; i < selfOrderItemIdList.size(); i++) {
                BarterOrderItem barterOrderItem = new BarterOrderItem();
                barterOrderItem.setItemId(selfOrderItemIdList.get(i));
                barterOrderItem.setOrderId(orderId);
                barterOrderItem.setOrderItemState(0);
                barterOrderItem.setUid1(uid);
            }
        }
        return orderId;
    }
}
