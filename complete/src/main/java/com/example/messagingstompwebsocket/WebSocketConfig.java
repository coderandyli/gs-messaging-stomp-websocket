package com.example.messagingstompwebsocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker // As its name suggests, @EnableWebSocketMessageBroker enables WebSocket message handling, backed by a message broker.
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	/**
	 * 配置消息代理(Message Broker)
	 * 	- 可以使用消息中间件作为消息代理，eg：RabbitMQ
	 * @param config
	 */
	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		// 设置消息代理SimpleBroker；SimpleBroker：是一个基于内存的消息代理
		// 在 Controller 类中的方法里面发生的消息，会首先转发到消息代理，从而发送到对应广播或者队列中。
		//
		config.enableSimpleBroker("/topic");
		// 定义客户端发送消息前缀，该前缀会筛选消息目标转发到 Controller 类中@MessageMapping注解对应的方法里 eg：/app/hello
		config.setApplicationDestinationPrefixes("/app");
	}

	/**
	 * 注册 STOMP 端点
	 *
	 * The registerStompEndpoints() method registers the /gs-guide-websocket endpoint,
	 * enabling SockJS fallback options so that alternate transports can be used if WebSocket is not available.
	 * The SockJS client will attempt to connect to /gs-guide-websocket and use the best available transport (websocket,
	 * xhr-streaming, xhr-polling, and so on).
	 *
	 * @param registry
	 */
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/gs-guide-websocket").withSockJS();
	}

}
