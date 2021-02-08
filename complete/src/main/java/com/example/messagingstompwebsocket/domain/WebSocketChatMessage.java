package com.example.messagingstompwebsocket.domain;

/**
 * @author lizhenzhen
 * @version 1.0
 * @date 2021/2/8 下午5:41
 */
public class WebSocketChatMessage {
    private String type;
    private String content;
    private String sender;
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getSender() {
        return sender;
    }
    public void setSender(String sender) {
        this.sender = sender;
    }
}
