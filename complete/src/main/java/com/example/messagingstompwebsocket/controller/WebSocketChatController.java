package com.example.messagingstompwebsocket.controller;

import com.example.messagingstompwebsocket.domain.WebSocketChatMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

/**
 * @author lizhenzhen
 * @version 1.0
 * @date 2021/2/8 下午5:52
 */
@Slf4j
@Controller
public class WebSocketChatController {

    @Autowired
    private SimpMessageSendingOperations simpMessageSendingOperations;

    @SendTo("/topic/javainuse")
    @MessageMapping("/chat.sendMessage")
    public WebSocketChatMessage sendMessage(@Payload WebSocketChatMessage webSocketChatMessage) {
        return webSocketChatMessage;
    }

    @SendTo("/topic/javainuse")
    @MessageMapping("/chat.newUser")
    public WebSocketChatMessage newUser(@Payload WebSocketChatMessage webSocketChatMessage,
                                        SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", webSocketChatMessage.getSender());
        return webSocketChatMessage;
    }

    @ResponseBody
    @PostMapping("/sys-msg/send")
    public void sendSysNoti() {
        WebSocketChatMessage webSocketChatMessage = new WebSocketChatMessage();
        webSocketChatMessage.setSender("sys");
//        webSocketChatMessage.setType("sys-msg");
        webSocketChatMessage.setType("message-data");
        webSocketChatMessage.setContent("夜深了，天凉了，注意盖好被子...");
        simpMessageSendingOperations.convertAndSend("/topic/javainuse", webSocketChatMessage);
    }
}
