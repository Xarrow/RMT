package io.github.xarrow.rmt.spring.boot.starter.autoconfigure;

import io.github.xarrow.rmt.spring.boot.starter.RmtStarterProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import javax.annotation.Resource;

/**
 * @Email: zhangjian12424@gmail.com.
 * @Author: helixcs
 * @Date: 6/17/2020.
 * @Desc:
 */
@EnableWebSocket
public class RmtWebSocketConfigurer implements WebSocketConfigurer {
    @Resource
    @Qualifier("terminalWebSocketHandler")
    private WebSocketHandler terminalWebSocketHandler;
    @Resource
    private RmtStarterProperties rmtStarterProperties;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(terminalWebSocketHandler, rmtStarterProperties.getWebsocketPath()).setAllowedOrigins("*");
    }

}
