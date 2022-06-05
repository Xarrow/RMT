package io.github.xarrow.rmt.api.configuration;

import io.github.xarrow.rmt.api.lifecycle.TerminalProcessLifecycle;
import io.github.xarrow.rmt.api.listener.TerminalProcessListener;
import io.github.xarrow.rmt.api.protocol.DefaultTerminalMessageQueue;
import io.github.xarrow.rmt.api.session.DefaultTerminalSession2ProcessManager;
import io.github.xarrow.rmt.api.session.DefaultTerminalSessionManager;
import io.github.xarrow.rmt.api.session.TerminalSession2ProcessManager;
import io.github.xarrow.rmt.api.websocket.TerminalHandler;
import io.github.xarrow.rmt.api.websocket.TerminalWsSessionProcessLifecycle;
import io.github.xarrow.rmt.api.listener.DefaultTerminalListenerManager;
import io.github.xarrow.rmt.api.listener.TerminalProcessListenerManager;
import io.github.xarrow.rmt.api.protocol.TerminalMessageQueue;
import io.github.xarrow.rmt.api.session.TerminalSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.PerConnectionWebSocketHandler;

/**
 * @Email: zhangjian12424@gmail.com.
 * @Author: helicxs
 * @Date: 6/17/2020.
 * @Desc:
 */
@Configuration
@EnableWebSocket
public class RmtConfiguration implements WebSocketConfigurer {

    //@Autowired
    //private TerminalProcessListener appStartBannerLoadListener;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(webSocketHandler(), "/terminal").setAllowedOrigins("*");
    }

    @Bean
    public WebSocketHandler webSocketHandler() {
        return new PerConnectionWebSocketHandler(TerminalHandler.class);
    }

    // lifecycle
    @Bean
    @Scope("prototype")
    public TerminalProcessLifecycle terminalProcessLifecycle() {
        return new TerminalWsSessionProcessLifecycle();
    }

    // 监听器管理
    @Bean
    public TerminalProcessListenerManager terminalProcessListenerManager() {
        return new DefaultTerminalListenerManager();
    }

    // messageQueue
    @Bean
    public TerminalMessageQueue<String> terminalMessageQueue() {
        return new DefaultTerminalMessageQueue();
    }

    // sessionManager
    @Bean
    public TerminalSessionManager terminalSessionManager() {
        return new DefaultTerminalSessionManager();
    }

    // session2processManager
    @Bean
    public TerminalSession2ProcessManager terminalSession2ProcessManager() {
        return new DefaultTerminalSession2ProcessManager();
    }

}
