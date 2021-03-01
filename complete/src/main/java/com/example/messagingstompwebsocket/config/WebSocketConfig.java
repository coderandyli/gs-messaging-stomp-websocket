package com.example.messagingstompwebsocket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

@Configuration
@EnableWebSocketMessageBroker // As its name suggests, @EnableWebSocketMessageBroker enables WebSocket message handling, backed by a message broker.
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	/**
	 * 配置消息代理(Message Broker)
	 */
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		// 设置消息代理

		// registry.setPathMatcher(new AntPathMatcher(".")); 切换分隔符

		registry.enableStompBrokerRelay("/topic") // 设置广播的基础路径
				.setRelayHost("192.168.30.12")
				.setRelayPort(61613)
				.setClientLogin("guest")
				.setClientPasscode("guest");
		// 定义客户端发送消息前缀，该前缀会筛选消息目标转发到 Controller 类中@MessageMapping注解对应的方法里
		registry.setApplicationDestinationPrefixes("/app"); // 设置客户端订阅消息的基础路径
	}

	/**
	 * 注册 STOMP 端点，客户端需要注册这个端点进行链接，withSockJS允许客户端利用sockjs进行浏览器兼容性处理
	 *
	 * The registerStompEndpoints() method registers the /gs-guide-websocket endpoint,
	 * enabling SockJS fallback options so that alternate transports can be used if WebSocket is not available.
	 * The SockJS client will attempt to connect to /gs-guide-websocket and use the best available transport (websocket,
	 * xhr-streaming, xhr-polling, and so on).
	 */
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/websocketApp")
				.addInterceptors(new HttpHandshakeInterceptor())
				.setHandshakeHandler(new HttpHandshakeHandler())
				.withSockJS();
	}


	/**
	 * 与处理从 WebSocket 客户端接收和发送到 WebSocket 的消息相关的配置选项。
	 *
	 * @param registry STOMP 端点
	 */
	@Override
	public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
		registry.addDecoratorFactory(new HttpWebSocketHandlerDecoratorFactory());
	}
}
