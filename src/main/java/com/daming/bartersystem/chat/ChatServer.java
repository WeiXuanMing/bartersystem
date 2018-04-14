package com.daming.bartersystem.chat;

import com.daming.bartersystem.service.ServiceFactory;
import com.daming.bartersystem.entitys.User;
import net.sf.json.JSONObject;
import org.codehaus.jackson.map.ObjectMapper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/websocket/{chatRoomId}/{user}")
public class ChatServer {

	private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	//private static Vector<Session> room = new Vector<Session>();
	//聊天室房间：维护一个Map代表聊天室的房间，聊天室有一个房间号，房间号作为Map的键，值为聊天的人的Set<session>，
	// 当一个人在房间里发送消息，获取set所有聊天的人的session并向所有人转发消息。
	private static Map<String,Set<Session>> chatRooms = new ConcurrentHashMap<>();


	private String uid;

	/**
	 * 用户接入
	 * @param session 可选
	 */
	@OnOpen
	public void onOpen(Session session,@PathParam(value = "user") String uid,@PathParam(value="chatRoomId") String chatRoomId){
		if(chatRooms.get(chatRoomId)==null) {
			//聊天室不存在
			Set<Session> sessionset = new HashSet<>();
			sessionset.add(session);
			chatRooms.put(chatRoomId, sessionset);
		}else {
			//聊天室存在
			Set<Session> sessionset = chatRooms.get(chatRoomId);
			System.out.println("用户"+session.getId()+"加入聊天室");
			sessionset.add(session);
		}

		//room.addElement(session);

	}

	/**
	 * 接收到来自用户的消息
	 * @param message
	 * @param session
	 */
	@OnMessage
	public void onMessage(String message,Session session,@PathParam(value="chatRoomId") String chatRoomId,@PathParam(value = "user") String uid){
		//把用户发来的消息解析为JSON对象
		JSONObject obj = JSONObject.fromObject(message);
		//向JSON对象中添加发送时间
		obj.put("date", df.format(new Date()));
		//向JSON对象中添加uid
		obj.put("uid",uid);
		//获取聊天室的各个用户的session
		Set<Session> sessionset = chatRooms.get(chatRoomId);
		for(Session se : sessionset) {
			System.out.println("现在聊天室里面有"+se.getId());
		}
		for(Session se : sessionset) {
			//设置消息是否为自己的
			obj.put("isSelf", se.equals(session));
			System.out.println("向"+se.getId()+"用户发送信息："+ obj);
//			//发送消息给远程用户
			se.getAsyncRemote().sendText(obj.toString());
			/*
			//遍历聊天室中的所有会话
			for(Session se : room){
				//设置消息是否为自己的
				obj.put("isSelf", se.equals(session));
				//发送消息给远程用户
				se.getAsyncRemote().sendText(obj.toString());
			}
			*/
		}
//		for(ChatRoom chatRoom : charRooms) {
//			Map<String,Session> userInfo = chatRoom.getUserInfo();
//
//		}
	}

	/**
	 * 用户断开
	 * @param session
	 */
	@OnClose
	public void onClose(Session session,@PathParam(value="chatRoomId") String chatRoomId){
		//room.remove(session);
		System.out.println("websocket关闭了");
		Set<Session> sessionset = chatRooms.get(chatRoomId);
		sessionset.remove(session);
		if(sessionset.isEmpty()) {
			chatRooms.remove(sessionset);
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
	/*
	public ChatRoom hasRoom(String uid) {
		for(ChatRoom chatRoom : charRooms) {
			Map<String,Session> userInfo = chatRoom.getUserInfo();
			if(userInfo.get(uid)!=null) {
				return chatRoom;
			}
		}
		return null;
	}
	*/
}
