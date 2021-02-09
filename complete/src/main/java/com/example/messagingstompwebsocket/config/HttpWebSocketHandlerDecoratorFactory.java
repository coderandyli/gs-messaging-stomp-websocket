package com.example.messagingstompwebsocket.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;
import org.springframework.web.socket.handler.WebSocketHandlerDecoratorFactory;

/**
 * WebSocket 处理器，用于处理握手前后阶段
 *
 * @author mydlq
 */
@Slf4j
@Configuration
public class HttpWebSocketHandlerDecoratorFactory implements WebSocketHandlerDecoratorFactory {

    /**
     * 配置 webSocket 处理器
     *
     * @param webSocketHandler webSocket 处理器
     * @return webSocket 处理器
     */
    @Override
    public WebSocketHandler decorate(WebSocketHandler webSocketHandler) {
        return new WebSocketHandlerDecorator(webSocketHandler) {
            /**
             * websocket 连接时执行的动作
             *
             * @param session    websocket session 对象
             * @throws Exception 异常对象
             */
            @Override
            public void afterConnectionEstablished(WebSocketSession session) throws Exception {
                log.info("用户上线");
                super.afterConnectionEstablished(session);

                // 输出进行 websocket 连接的用户信息
//                if (session.getPrincipal() != null) {
//                    String username = session.getPrincipal().getName();
//                    log.info("用户:" + username + "上线");
//                    super.afterConnectionEstablished(session);
//                }
            }

            /**
             * websocket 关闭连接时执行的动作
             * @param session websocket session 对象
             * @param closeStatus 关闭状态对象
             * @throws Exception 异常对象
             */
            @Override
            public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
                log.info("用户下线");
                super.afterConnectionClosed(session, closeStatus);

//                // 输出关闭 websocket 连接的用户信息
//                if (session.getPrincipal() != null) {
//                    String username = session.getPrincipal().getName();
//                    log.info("用户:" + username + "下线");
//                    super.afterConnectionClosed(session, closeStatus);
//                }
            }

        };
    }

}
