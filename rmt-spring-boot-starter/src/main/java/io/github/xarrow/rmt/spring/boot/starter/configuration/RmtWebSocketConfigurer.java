package io.github.xarrow.rmt.spring.boot.starter.configuration;

import io.github.xarrow.rmt.spring.boot.starter.RmtStarterProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * @Email: zhangjian12424@gmail.com.
 * @Author: helicxs
 * @Date: 6/17/2020.
 * @Desc:
 */
@EnableWebSocket
public class RmtWebSocketConfigurer implements WebSocketConfigurer {
    @Autowired
    private WebSocketHandler webSocketHandler;
    @Autowired
    private RmtStarterProperties rmtStarterProperties;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(webSocketHandler, rmtStarterProperties.getWebsocketPath()).setAllowedOrigins("*");
    }


}
