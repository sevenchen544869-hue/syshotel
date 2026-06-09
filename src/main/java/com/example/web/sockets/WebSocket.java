package com.example.web.sockets;

import com.example.web.dto.WechatMessageDto;
import com.example.web.dto.query.WechatMessagePagedInput;
import com.example.web.service.WechatCollectionService;
import com.example.web.service.WechatMessageService;
import com.example.web.tools.BaseContext;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 不同的userId会创建不同的实例
 * 相同的userId在@OnMessage每次请求接收到消息时，使用的同一个实例接收
 */
@ServerEndpoint(value = "/webscoket/{userId}")
@Component
public class WebSocket {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().registerModule(new JavaTimeModule());

    /**
     * 存放所有在线的客户端
     */
    private static ConcurrentHashMap<String, WebSocket> webSocketMap = new ConcurrentHashMap<>();
    /**
     * 连接uid和连接会话
     */
    private String userId;
    private Session session;


   


    //新增一个方法用于主动向客户端发送消息
    public static void sendMessage(String message, String userId) {
        WebSocket webSocket = webSocketMap.get(userId);
        if (webSocket != null) {
            try {

                webSocket.session.getBasicRemote().sendText(message);
                System.out.println("【websocket消息】发送消息成功,目标用户id=" + userId + ",消息内容：" + message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("【websocket消息】发送消息失败,用户id=" + userId + "对方没有上线");
        }
    }


    //前端请求时一个websocket时
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        this.userId = userId;
        this.session = session;
        webSocketMap.put(userId, this);
        System.out.println("【websocket消息】有新的连接,连接id=" + userId + ":" + this);
    }

    //前端关闭时一个websocket时
    @OnClose
    public void onClose(@PathParam("userId") String userId) {
        webSocketMap.remove(userId);
        System.out.println("【websocket消息】连接断开:" + userId);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("【websocket消息】WebSocket发生错误，错误信息为：" + error.getMessage());
        error.printStackTrace();
    }

    //前端向后端发送消息
    @OnMessage
    public void onMessage(String message) throws JsonProcessingException {


        WechatMessageService _WechatMessageService = BaseContext.getApplicationContext().getBean(WechatMessageService.class);
        WechatCollectionService _WechatCollectionService = BaseContext.getApplicationContext().getBean(WechatCollectionService.class);

        System.out.println("【websocket消息】收到客户端发来的消息:" + message);
        //对消息进行反序列化

        SocketMessage socketMessage = ToObject(message, SocketMessage.class);


        //如果是发送聊天消息
        if ("Wechat_SendMessage".equals(socketMessage.getType())) {
            //写入聊天消息到数据库
            WechatMessageDto wechatMessage = ToObject(socketMessage.getData(), WechatMessageDto.class);


            wechatMessage.setSendTime(LocalDateTime.now());

            //查询该聊天消息
            Integer messageId = _WechatMessageService.CreateOrEdit(wechatMessage).getId();
            WechatMessagePagedInput wechatMessagePagedInput = new WechatMessagePagedInput();
            wechatMessagePagedInput.setId(messageId);
            WechatMessageDto wechatMessageDto = _WechatMessageService.Get(wechatMessagePagedInput);

            //同步到会话列表
            _WechatCollectionService.SyncWechatCollection(wechatMessageDto.getSelfUserId(), wechatMessageDto.getOtherUserId(), wechatMessageDto.getContent());


            //返回消息给双方
            SocketMessage backSocketMessage = new SocketMessage();
            backSocketMessage.setType("Wechat_SendMessage_Back");

            backSocketMessage.setData(this.ToJsonString(wechatMessageDto));
            //发送给自己和对方
            sendMessage(this.ToJsonString(backSocketMessage), String.valueOf(wechatMessage.getSelfUserId()));
            sendMessage(this.ToJsonString(backSocketMessage), String.valueOf(wechatMessage.getOtherUserId()));
        }


    }
	 /**
     * 得到所有的在线用户
     *
     * @return
     */
    public static ConcurrentHashMap<String, WebSocket> GetWebSocketMap() {
        return webSocketMap;
    }
    /**
     * 判断是否在线
     *
     * @param userId
     * @return
     */
    public static Boolean IsOnline(String userId) {
        return webSocketMap.get(userId) != null;
    }
    /**
     * 对象转换成字符串
     */
    public String ToJsonString(Object obj) throws JsonProcessingException {
        return OBJECT_MAPPER.writeValueAsString(obj);
    }

    /**
     * 字符串转换成对象
     */
    public <T> T ToObject(String str, Class<T> target) throws JsonProcessingException {
        return OBJECT_MAPPER.readValue(str, target);
    }
}
 