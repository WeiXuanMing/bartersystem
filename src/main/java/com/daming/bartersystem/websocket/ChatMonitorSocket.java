package com.daming.bartersystem.websocket;

import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.PathVariable;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/ChatMonitorSocket/{orderId}")
public class ChatMonitorSocket {
    /*监听与推送邀请进入聊天室的websocket*/
    //维护一个以订单号为键，值为一个保存session的set的set
    private static Map<String,Set<Session>> sessionMap = new ConcurrentHashMap<>();

    /**
     * 用户接入
     * @param session 可选
     */
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "orderId") String orderId){
        Set<Session> sessions = sessionMap.get(orderId);
        if (sessions == null){
            //记录不存在，就新建一个
            sessions = new HashSet<>();
            sessions.add(session);
            sessionMap.put(orderId,sessions);
        }else {
            //记录存在，则直接添加
            sessions.add(session);
        }
    }

    /**
     * 接收到来自用户的消息
     * @param message
     * @param session
     */
    @OnMessage
    public void onMessage(String message, Session session, @PathParam(value = "orderId") String orderId){
        //把用户发来的消息解析为JSON对象
        //JSON包含发起人Id,发起人nickname，聊天室房间号
        JSONObject obj = JSONObject.fromObject(message);
        System.out.println("接受到的邀请信息是"+message);
        Set<Session> sessions = sessionMap.get(orderId);
        for(Session se : sessions) {
            System.out.println("现在聊天室里面有"+se.getId());
            se.getAsyncRemote().sendText(message);
        }
    }

    /**
     * 用户断开
     * @param session
     */
    @OnClose
    public void onClose(Session session,@PathParam(value = "orderId") String orderId){
        System.out.println("websocket关闭了");
        Set<Session> sessions = sessionMap.get(orderId);
        sessions.remove(session);
        if (sessions.isEmpty()){
            sessionMap.remove(orderId);
        }
    }

    /**
     * 用户连接异常
     * @param t
     */
    @OnError
    public void onError(Throwable t){
        t.printStackTrace();
        System.out.println(t.getMessage());
    }
}
