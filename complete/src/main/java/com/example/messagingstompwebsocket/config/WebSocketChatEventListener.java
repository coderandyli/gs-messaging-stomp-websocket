package com.example.messagingstompwebsocket.config;

import com.example.messagingstompwebsocket.domain.WebSocketChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

/**
 * websocket 事件监听
 *
 * @author lizhenzhen
 * @version 1.0
 * @date 2021/2/8 下午5:49
 */
@Component
public class WebSocketChatEventListener {
    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    /**
     * STOMP会话已完全建立【事件】
     *
     * @param event
     */
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        System.out.println("Received a new web socket connection");
    }

    /**
     * STOMP会话结束【事件】
     *  - 断开连接可能是客户端发起的，也有可能是Websocket会话关闭时自动生成的。在某些情况下该事件可能在每个会话中发布不止一次，
     *  因此组件对于多个断开连接事件应该是幂等的。
     * @param event
     */
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) headerAccessor.getSessionAttributes().get("username");

        if(username != null) {
            System.out.println(username + " has left the chat");

            WebSocketChatMessage chatMessage = new WebSocketChatMessage();
            chatMessage.setType("Leave");
            chatMessage.setSender(username);
//            messagingTemplate.convertAndSend("/topic/public", chatMessage);
            messagingTemplate.convertAndSend("/topic/javainuse", chatMessage);
        }
    }
}
