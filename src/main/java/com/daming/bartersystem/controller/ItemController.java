package com.daming.bartersystem.controller;

import com.daming.bartersystem.DTO.*;
import com.daming.bartersystem.configurator.JedisConfigurator;
import com.daming.bartersystem.entitys.Item;
import com.daming.bartersystem.entitys.UserReview;
import com.daming.bartersystem.service.ItemService;
import com.daming.bartersystem.service.Item_ImageService;
import com.daming.bartersystem.service.ReviewService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.org.apache.regexp.internal.RE;
import net.sf.json.JSONObject;
import org.apdplat.word.WordSegmenter;
import org.apdplat.word.segmentation.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

@Controller
@SessionAttributes(value = {"uid"})
public class ItemController {
    @Autowired
    private ItemService itemService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private Item_ImageService item_imageService;

    private static JedisPool jedisPool = new JedisPool(JedisConfigurator.ip,JedisConfigurator.port);
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
            Jedis jedis = jedisPool.getResource();
            List<Word> words = WordSegmenter.seg(item_title);
            for (int i = 0;i < words.size();i++){
                System.out.println(words.get(i));
                jedis.sadd("wordset",words.get(i).getText());
                jedis.sadd(words.get(i).getText(),item_title);
                System.out.println("redis中保存的分词set集合有这些分词:"+jedis.smembers("wordset"));
                System.out.println("redis中保存的"+words.get(i).getText()+"集合有这些标题:"+jedis.smembers(words.get(i).getText()));
            }
            System.out.println("分词保存succeed******************************************************");
            jedis.close();
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
                result = new Result(0,"succeed",item);
                return result;
            }
        }else {
            //not login
            result = new Result(0,"isNotLogin",null);
            return  result;
        }
        return result;
    }
    //写一个url专门针对用户在搜索框中输入触发的onkeyup时间
    @RequestMapping(value = "/onSearchValueChange",method = RequestMethod.POST, consumes = "application/json",produces="application/json")
    @ResponseBody
    public Result<List<String>> onSearchValueChange(@RequestBody String param) throws UnsupportedEncodingException {
        Jedis jedis = jedisPool.getResource();
        System.out.println("接受到的json格式是这个样子的："+param);
        JSONObject jsonObject = JSONObject.fromObject(param);
        System.out.println("解析后的"+jsonObject);
        String word = (String) jsonObject.get("word");
        Set<String> wordset = jedis.smembers("wordset");
        String pattern = ".*"+word+".*";
        Iterator<String> iterator = wordset.iterator();
        List<String> titlelist = new ArrayList<>();
        while (iterator.hasNext()){
            String s = iterator.next();
            boolean isMatch = Pattern.matches(pattern,s);
            if (isMatch){
                System.out.println(s);
                Set<String> settitlelist= jedis.smembers(s);
                titlelist.addAll(settitlelist);
                System.out.println("titlelist里面有这些东西:"+titlelist);
                System.out.println(jedis.smembers(s));
            }
        }
        jedis.close();
        Result<List<String>> result = new Result<>(0,"succeed",titlelist);
        return result;
    }
}
