package com.daming.bartersystem.controller;

import com.daming.bartersystem.DTO.*;
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
     * 需要提供orderId、receiver_address和selfOrderItemIdList{item_id,item_id}等信息
     *
     *
     * 返回的message有：
     * failure：失败
     * isNotYouItem：存在不是物主的物品
     * isNotLogin：未登录
     *
     * @param
     * @param orderId
     * @return
     */


    @RequestMapping(value = "/submitOrderItem/{orderId}", method = RequestMethod.POST, produces="application/json")
    @ResponseBody
    public Result<SubmitOrderItemResult> submitOrderItem(@RequestBody String param,@PathVariable("orderId") Integer orderId,HttpSession session) throws IOException {
        /*
        * 实现思路，先解析Json，获取到orderId,receiver_address和selfOrderItemIdList（itemId数组的）的数据
        * 先检验是否是本人的ItemId,如果都是，执行下一步-->
        * 根据orderId和Uid获取到BarterOrder实例，根据 BarterOrder的itemid属性获取物主id，
        * 然后根据orderId,用户uid,物主uid，itemId，收货地址，将orderItem状态设置成1；（0；）创建barterorderItem
        * 将barterorderItem加入数据表中，然后将返回的barterorderItemid和barter_order_id创建BarterOrder_OrderItem然后加入数据表中
        * 然后根据orderId,
        * */
        Result<SubmitOrderItemResult> result = new Result<SubmitOrderItemResult>(0,"failure",new SubmitOrderItemResult());
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
            //已登录
            System.out.println("已经登录了耶~~(＾－＾)V");
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
            List<Item> items = new ArrayList<Item>();
            boolean isSelfItem = true;
            //检查是否是本人的物品
            for (int i = 0;i < selfOrderItemIdList.size();i++) {
                 Item item = itemService.query(selfOrderItemIdList.get(i));
                 if (!item.getUid().equals(uid)){
                    isSelfItem = false;
                    break;
                 }
                items.add(item);
            }
            if (isSelfItem){


                //都是自己的物品
                System.out.println("全是自己的物品耶~~o(∩_∩)o ");
                BarterOrder barterOrder = orderService.queryByOrderId(orderId);
                Integer ownerItemId = barterOrder.getItemId();
                Item item = itemService.query(ownerItemId);
                Integer ownerId = item.getUid();

                //为每一个item创建一个BarterOrderItem，并存入
                for (int i = 0;i < selfOrderItemIdList.size();i++) {
                    Integer submitItemId =  selfOrderItemIdList.get(i);
                    BarterOrderItem barterOrderItem1 = orderItemService.queryByOrderIdUidUidItemId(orderId, uid, ownerId, submitItemId);
                    if (barterOrderItem1 !=null){
                        //已经存在了，不添加，但是改动收货地址
                        //判断是否已经提交过,根据orderId,uid1,uid2,item_id获取
                        barterOrderItem1.setReceiverAddress(receiver_address);
                        orderItemService.updateByBarterOrderItem(barterOrderItem1);
                    }else {
                        BarterOrderItem barterOrderItem = new BarterOrderItem();
                        barterOrderItem.setOrderId(orderId);
                        barterOrderItem.setUid1(uid);
                        barterOrderItem.setUid2(ownerId);
                        barterOrderItem.setItemId(submitItemId);
                        barterOrderItem.setReceiverAddress(receiver_address);
                        barterOrderItem.setOrderItemState(1);

                        System.out.printf("物品编号：" + selfOrderItemIdList.get(i) + "插入数据表" + orderItemService.insert(barterOrderItem));
                        System.out.println("物品编号：" + selfOrderItemIdList.get(i) + "插入数据表" + barterOrderItem);
                        BarterOrder_OrderItem barterOrder_orderItem = new BarterOrder_OrderItem();
                        barterOrder_orderItem.setBarterOrderId(orderId);
                        barterOrder_orderItem.setOrderitemId(barterOrderItem.getOrderitemId());
                        barterOrder_orderItem.setAbandoned(0);
                        System.out.printf(barterOrder_orderItem + "：插入数据表" + barterOrder_orderItemService.addBarterOrder_OrderItem(barterOrder_orderItem));
                        System.out.printf("插入的关系表的barterOrder_orderItem的详情" + barterOrder_orderItem);
                    }
                }
                result = new Result<SubmitOrderItemResult>(0,"succeed",new SubmitOrderItemResult());
                return result;
            }else {
                result = new Result<SubmitOrderItemResult>(0,"isNotYouItem",new SubmitOrderItemResult());
                return result;
            }
        }else {
            //未登录
            result = new Result<SubmitOrderItemResult>(0,"isNotLogin",new SubmitOrderItemResult());
            return result;
        }
    }

    //作为物主获取物品别人申请的订单
    /*
    * 需要获取selfOrderItemList和applicantOrderItemList
    *
    * 返回信息：
    * NotYouItem：不是有关你的订单
    * failure: 未知错误
    *isNotLogin:未登录
    *isNotYouItem：存在不是物主的物品
    * NotThisOrder: 没有这个订单
    *请求参数是订单编号和提出交换申请的人的编号
    * 首先获取订单信息，根据订单信息中的itemId判断是不是物主，不是直接返回"isNotYouItem"，
    * 如果是物主，在根据订单号到订单和订单详情表里取相关的映射关系，然后把所有有关的订单详细表取出，分别存在selfOrderItemList和applicantOrderItemList数组里面
    * 最后将结果返回
    *
    * */
    @RequestMapping(value = "/getItemOwnerExchangeOrder/{orderId}", method = RequestMethod.GET)
    @ResponseBody
    public Result<ItemOwnerExchangeOrderResult> getApplyExchangeOrder(@PathVariable("orderId") Integer orderId, HttpSession session){
        Result<ItemOwnerExchangeOrderResult> result = new Result<ItemOwnerExchangeOrderResult>(0,"failure",new ItemOwnerExchangeOrderResult());
        Integer uid = (Integer) session.getAttribute("uid");
        List<BarterOrderItem> selfOrderItemList = new ArrayList<BarterOrderItem>();
        List<BarterOrderItem> applicantOrderItemList = new ArrayList<BarterOrderItem>();
        if (uid != null){
            //已登录
            BarterOrder barterOrder = orderService.queryByOrderId(orderId);
            if (barterOrder != null) {
                Integer itemId = barterOrder.getItemId();
                Item item = itemService.query(itemId);
                if (item.getUid().equals(uid)) {
                    //是本人的物品
                    List<BarterOrder_OrderItem> barterOrder_orderItems = barterOrder_orderItemService.queryByOrderId(orderId);
                    for (int i = 0; i < barterOrder_orderItems.size(); i++) {
                        BarterOrder_OrderItem barterOrder_orderItem = barterOrder_orderItems.get(i);
                        BarterOrderItem barterOrderItem = orderItemService.queryByOrderItemId(barterOrder_orderItem.getOrderitemId());
                        if (barterOrderItem.getUid1().equals(uid)) {
                            selfOrderItemList.add(barterOrderItem);
                        } else {
                            applicantOrderItemList.add(barterOrderItem);
                        }
                    }
                    ItemOwnerExchangeOrderResult itemOwnerExchangeOrderResult = new ItemOwnerExchangeOrderResult();
                    itemOwnerExchangeOrderResult.setSelfOrderItemList(selfOrderItemList);
                    itemOwnerExchangeOrderResult.setApplicantOrderItemList(applicantOrderItemList);
                    result = new Result<ItemOwnerExchangeOrderResult>(0, "succeed", itemOwnerExchangeOrderResult);
                    return result;
                } else {
                    result = new Result<ItemOwnerExchangeOrderResult>(0, "isNotYouItem", new ItemOwnerExchangeOrderResult());
                    return result;
                }
            }else {
                result = new Result<ItemOwnerExchangeOrderResult>(0, "NotThisOrder", new ItemOwnerExchangeOrderResult());
                return result;
            }
        }else{
            //未登录
            result = new Result<ItemOwnerExchangeOrderResult>(0,"isNotLogin",new ItemOwnerExchangeOrderResult());
            return result;
        }

    }


}
