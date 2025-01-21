package com.transporthc.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Configuration
@EnableWebSocketMessageBroker
@Slf4j
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Autowired
    private CustomJWTDecoder jwtDecoder;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOrigins("*")
                .addInterceptors(new HandshakeInterceptor() {
                    @Override
                    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                                   WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {

                        String authToken=request.getHeaders().getFirst("Authorization");
                        log.info("Authorization Token: " + authToken);
                        if (authToken == null || !authToken.startsWith("Bearer ")) {
                            return false;
                        }
                        String token = authToken.substring(7);
                        log.info("token:"+ token);
                        try {
                            Jwt decodedJwt = jwtDecoder.decode(token);
                            String username = decodedJwt.getClaim("sub").toString();
                            attributes.put("username", username);
                        } catch (Exception e) {
                            return false;
                        }
                        return true;
                    }

                    @Override
                    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                               WebSocketHandler wsHandler, Exception exception) {

                    }
                });
                //.withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {//thiết lập cách thức các thông điệp sẽ được gửi và nhận qua WebSocket
        registry.setApplicationDestinationPrefixes("/app"); // Tiền tố cho client gửi tới server
        registry.enableSimpleBroker("/chatroom","/user","/group");// kích hoạt message broker: giúp xử lý thông điệp gửi từ server cho các kênh chung và riêng
        registry.setUserDestinationPrefix("/user");//cho phép tạo các kênh riêng cho từng người dùng
    }
}
//Kênh riêng cho người dùng là địa chỉ mà bạn sử dụng để gửi thông điệp tới người dùng cụ thể.
//Gửi thông điệp riêng là hành động gửi tin nhắn vào các kênh đó.